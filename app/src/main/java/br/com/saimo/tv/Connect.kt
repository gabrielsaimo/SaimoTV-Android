package br.com.saimo.tv

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Base64
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.OutputStream
import java.net.Inet4Address
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.random.Random

/**
 * Servidor do Saimo Connect para TV Box.
 *
 * Implementa na porta 8999:
 * 1. GET /info — Descoberta HTTP rápida pela rede local.
 * 2. GET /ws   — Conexão WebSocket em tempo real para controle remoto,
 *                espelhamento de estado e controle de áudio independente da multi-tela.
 * 3. Anúncio mDNS (_saimotv._tcp) como suporte adicional à descoberta.
 */
@UnstableApi
object Connect {

    const val PORTA = 8999
    private const val TAG = "SaimoConnect"
    private const val PREFS_NAME = "saimo_connect"
    private const val KEY_TV_ID = "tv_uuid"
    private const val KEY_TRUSTED_CIDS = "trusted_cids"
    private const val GUID_WEBSOCKET = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11"

    @Volatile
    var ativo = false
        private set

    @Volatile
    var tvId: String = ""
        private set

    @Volatile
    var pinAtual: String = "123456"
        private set

    @Volatile
    var tvAudio: String = "0" // "0" (Tela 1), "1" (Tela 2) ou "mute"

    private var serverSocket: ServerSocket? = null
    private var threadServidor: Thread? = null
    private var nsdManager: NsdManager? = null
    private var nsdRegistrationListener: NsdManager.RegistrationListener? = null
    private val clientes = CopyOnWriteArrayList<ClienteWebSocket>()
    private val trustedCids = mutableSetOf<String>()
    private val handler = Handler(Looper.getMainLooper())

    /** Interface que a Activity em primeiro plano (ex: MainActivity) implementa */
    interface Listener {
        fun obterEstado(): JSONObject
        fun sintonizarCanal(nome: String, metade: Int?)
        fun alternarMultiTela(ligar: Boolean)
        fun definirMetadeAtiva(metade: Int)
        fun definirTvAudio(som: String)
        fun trocarFonte(delta: Int)
        fun simularTecla(keyCode: Int): Boolean
        fun abrirVod(chave: String, temp: Int?, ep: Int?, fonte: Int?)
        fun pausarOuRetomarVod()
        fun avancarOuVoltarVod(ms: Long, delta: Long?)
        fun ajustarVolume(delta: Int?, set: Float?, mute: Boolean?)
        fun preencherTexto(texto: String)
        fun onPhoneAudioChanged(phoneAudioList: List<PhoneAudioStatus>)
        fun solicitarAprovacao(nomeDispositivo: String, aoResponder: (permitido: Boolean) -> Unit)
    }

    data class PhoneAudioStatus(
        val cid: String,
        val name: String,
        val src: String // "0", "1", "both", "none"
    )

    @Volatile
    var listener: Listener? = null

    private val phoneAudioMap = java.util.concurrent.ConcurrentHashMap<String, PhoneAudioStatus>()

    fun iniciar(context: Context) {
        if (ativo) return
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        tvId = prefs.getString(KEY_TV_ID, null) ?: UUID.randomUUID().toString().also {
            prefs.edit().putString(KEY_TV_ID, it).apply()
        }
        val salvos = prefs.getStringSet(KEY_TRUSTED_CIDS, null)
        if (salvos != null) {
            synchronized(trustedCids) { trustedCids.addAll(salvos) }
        }
        gerarNovoPin()
        ativo = true

        threadServidor = Thread({
            rodarServidor(context.applicationContext)
        }, "saimo-connect-server").apply {
            isDaemon = true
            start()
        }

        registrarNsd(context.applicationContext)
    }

    fun parar(context: Context) {
        ativo = false
        runCatching { serverSocket?.close() }
        serverSocket = null
        for (c in clientes) {
            c.fechar()
        }
        clientes.clear()
        desregistrarNsd()
    }

    fun gerarNovoPin(): String {
        pinAtual = String.format("%06d", Random.nextInt(100000, 999999))
        return pinAtual
    }

    fun confiarCid(context: Context, cid: String) {
        synchronized(trustedCids) {
            trustedCids.add(cid)
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putStringSet(KEY_TRUSTED_CIDS, HashSet(trustedCids))
                .apply()
        }
    }

    fun ehCidConfiavel(cid: String): Boolean {
        synchronized(trustedCids) {
            return trustedCids.contains(cid)
        }
    }

    fun obterIpLocal(): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val iface = interfaces.nextElement()
                if (iface.isLoopback || !iface.isUp) continue
                val addrs = iface.inetAddresses
                while (addrs.hasMoreElements()) {
                    val addr = addrs.nextElement()
                    if (addr is Inet4Address && !addr.isLoopbackAddress) {
                        return addr.hostAddress ?: ""
                    }
                }
            }
        } catch (_: Exception) {}
        return "127.0.0.1"
    }

    fun nomeDaTv(): String {
        val modelo = Build.MODEL.trim()
        return if (modelo.contains("TV", ignoreCase = true) || modelo.contains("Box", ignoreCase = true)) {
            "Saimo TV ($modelo)"
        } else {
            "Saimo TV - $modelo"
        }
    }

    /** Dispara o estado atual da TV para todos os celulares conectados */
    fun notificarMudancaDeEstado() {
        if (clientes.isEmpty()) return
        handler.post {
            try {
                val estado = obterEstadoAtual()
                val payload = estado.toString()
                for (c in clientes) {
                    if (c.autenticado) {
                        c.enviarTexto(payload)
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "erro ao enviar estado", e)
            }
        }
    }

    fun obterEstadoAtual(): JSONObject {
        val lis = listener
        val estado = if (lis != null) {
            try {
                lis.obterEstado()
            } catch (e: Exception) {
                JSONObject().apply {
                    put("t", "state")
                    put("mode", "live")
                    put("playing", true)
                }
            }
        } else {
            JSONObject().apply {
                put("t", "state")
                val ativ = SaimoApp.atividadeAtual
                put("mode", "idle")
                put("screen", when (ativ) {
                    is EscolhaActivity -> "home"
                    is AjustesActivity -> "settings"
                    is GradeActivity -> "guide"
                    is BuscaActivity -> "search"
                    else -> "idle"
                })
                put("channel", "")
                put("playing", false)
                put("multi", false)
                put("active", 0)
                put("screens", JSONArray())
            }
        }
        estado.put("tvAudio", tvAudio)
        val audioArr = JSONArray()
        for (status in phoneAudioMap.values) {
            audioArr.put(JSONObject().apply {
                put("cid", status.cid)
                put("name", status.name)
                put("src", status.src)
            })
        }
        estado.put("phoneAudio", audioArr)
        return estado
    }

    fun solicitarAprovacaoNaUi(nomeDispositivo: String, aoResponder: (permitido: Boolean) -> Unit) {
        handler.post {
            val act = SaimoApp.atividadeAtual
            if (act != null && !act.isFinishing && !act.isDestroyed) {
                try {
                    val dialog = AlertDialog.Builder(act)
                        .setTitle(R.string.connect_aprovar_titulo)
                        .setMessage(act.getString(R.string.connect_aprovar_msg, nomeDispositivo))
                        .setPositiveButton(R.string.connect_permitir) { _, _ -> aoResponder(true) }
                        .setNegativeButton(R.string.connect_recusar) { _, _ -> aoResponder(false) }
                        .setOnCancelListener { aoResponder(false) }
                        .create()
                    dialog.show()
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.requestFocus()
                    return@post
                } catch (e: Exception) {
                    Log.w(TAG, "erro ao exibir diálogo de aprovação", e)
                }
            }
            val lis = listener
            if (lis != null) {
                lis.solicitarAprovacao(nomeDispositivo, aoResponder)
            } else {
                aoResponder(true)
            }
        }
    }

    /** Avisa aos celulares que o teclado deve abrir */
    fun notificarAberturaDeTexto(dica: String, valorAtual: String) {
        val msg = JSONObject().apply {
            put("t", "textRequest")
            put("hint", dica)
            put("value", valorAtual)
        }.toString()
        for (c in clientes) if (c.autenticado) c.enviarTexto(msg)
    }

    fun notificarFechamentoDeTexto() {
        val msg = JSONObject().apply { put("t", "textDone") }.toString()
        for (c in clientes) if (c.autenticado) c.enviarTexto(msg)
    }

    private fun rodarServidor(context: Context) {
        try {
            serverSocket = ServerSocket().apply {
                reuseAddress = true
                bind(InetSocketAddress(PORTA))
            }
            Log.i(TAG, "Saimo Connect escutando em ${obterIpLocal()}:$PORTA")
            while (ativo) {
                val socket = serverSocket?.accept() ?: break
                Thread({
                    tratarConexao(context, socket)
                }, "connect-client-${socket.inetAddress.hostAddress}").start()
            }
        } catch (e: Exception) {
            if (ativo) Log.w(TAG, "Servidor Saimo Connect encerrado com erro", e)
        }
    }

    private fun tratarConexao(context: Context, socket: Socket) {
        try {
            socket.tcpNoDelay = true
            val input = socket.getInputStream()
            val output = socket.getOutputStream()

            // Lê requisição HTTP inicial (máx 8 KB)
            val cabecalhoBytes = ByteArray(8192)
            var lidos = 0
            while (lidos < cabecalhoBytes.size) {
                val b = input.read()
                if (b == -1) break
                cabecalhoBytes[lidos++] = b.toByte()
                if (lidos >= 4 &&
                    cabecalhoBytes[lidos - 4] == '\r'.code.toByte() &&
                    cabecalhoBytes[lidos - 3] == '\n'.code.toByte() &&
                    cabecalhoBytes[lidos - 2] == '\r'.code.toByte() &&
                    cabecalhoBytes[lidos - 1] == '\n'.code.toByte()
                ) {
                    break
                }
            }

            val requisicao = String(cabecalhoBytes, 0, lidos, StandardCharsets.UTF_8)
            val primeiraLinha = requisicao.lineSequence().firstOrNull() ?: ""

            // 1. GET /info (Descoberta HTTP rápida)
            if (primeiraLinha.startsWith("GET /info")) {
                val infoJson = JSONObject().apply {
                    put("app", "saimo-tv")
                    put("id", tvId)
                    put("name", nomeDaTv())
                    put("version", "2.1.7")
                    put("port", PORTA)
                    put("multi", true)
                }.toString()

                val resposta = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: application/json; charset=utf-8\r\n" +
                        "Access-Control-Allow-Origin: *\r\n" +
                        "Access-Control-Allow-Methods: GET, OPTIONS\r\n" +
                        "Connection: close\r\n" +
                        "Content-Length: ${infoJson.toByteArray(StandardCharsets.UTF_8).size}\r\n\r\n" +
                        infoJson
                output.write(resposta.toByteArray(StandardCharsets.UTF_8))
                output.flush()
                socket.close()
                return
            }

            // OPTIONS para CORS pré-voo
            if (primeiraLinha.startsWith("OPTIONS")) {
                val resposta = "HTTP/1.1 204 No Content\r\n" +
                        "Access-Control-Allow-Origin: *\r\n" +
                        "Access-Control-Allow-Methods: GET, OPTIONS\r\n" +
                        "Access-Control-Allow-Headers: *\r\n" +
                        "Connection: close\r\n\r\n"
                output.write(resposta.toByteArray(StandardCharsets.UTF_8))
                output.flush()
                socket.close()
                return
            }

            // 2. GET /ws (WebSocket Upgrade)
            if (requisicao.contains("Upgrade: websocket", ignoreCase = true)) {
                val keyMatch = "Sec-WebSocket-Key:\\s*([^\\r\\n]+)".toRegex(RegexOption.IGNORE_CASE).find(requisicao)
                val secKey = keyMatch?.groupValues?.get(1)?.trim()
                if (secKey != null) {
                    val digest = MessageDigest.getInstance("SHA-1")
                    val acceptBytes = digest.digest((secKey + GUID_WEBSOCKET).toByteArray(StandardCharsets.ISO_8859_1))
                    val secAccept = Base64.encodeToString(acceptBytes, Base64.NO_WRAP)

                    val handshake = "HTTP/1.1 101 Switching Protocols\r\n" +
                            "Upgrade: websocket\r\n" +
                            "Connection: Upgrade\r\n" +
                            "Sec-WebSocket-Accept: $secAccept\r\n\r\n"
                    output.write(handshake.toByteArray(StandardCharsets.ISO_8859_1))
                    output.flush()

                    val cliente = ClienteWebSocket(context, socket, input, output)
                    clientes.add(cliente)
                    cliente.rodarLoop()
                    clientes.remove(cliente)
                    cliente.cid?.let { phoneAudioMap.remove(it) }
                    notificarMudancaDeEstado()
                    return
                }
            }

            // Outras rotas desconhecidas
            val resposta404 = "HTTP/1.1 404 Not Found\r\nConnection: close\r\n\r\n"
            output.write(resposta404.toByteArray(StandardCharsets.UTF_8))
            output.flush()
            socket.close()
        } catch (e: Exception) {
            runCatching { socket.close() }
        }
    }

    private fun registrarNsd(context: Context) {
        try {
            nsdManager = context.getSystemService(Context.NSD_SERVICE) as? NsdManager
            val serviceInfo = NsdServiceInfo().apply {
                serviceName = nomeDaTv()
                serviceType = "_saimotv._tcp."
                port = PORTA
            }
            nsdRegistrationListener = object : NsdManager.RegistrationListener {
                override fun onServiceRegistered(p0: NsdServiceInfo?) {
                    Log.i(TAG, "mDNS registrado: ${p0?.serviceName}")
                }
                override fun onRegistrationFailed(p0: NsdServiceInfo?, p1: Int) {
                    Log.w(TAG, "Falha ao registrar mDNS: $p1")
                }
                override fun onServiceUnregistered(p0: NsdServiceInfo?) {}
                override fun onUnregistrationFailed(p0: NsdServiceInfo?, p1: Int) {}
            }
            nsdManager?.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, nsdRegistrationListener)
        } catch (e: Exception) {
            Log.w(TAG, "mDNS não suportado neste aparelho", e)
        }
    }

    private fun desregistrarNsd() {
        try {
            nsdRegistrationListener?.let { nsdManager?.unregisterService(it) }
        } catch (_: Exception) {}
        nsdRegistrationListener = null
        nsdManager = null
    }

    /** Gerencia a sessão de um celular conectado via WebSocket */
    private class ClienteWebSocket(
        private val context: Context,
        private val socket: Socket,
        private val input: InputStream,
        private val output: OutputStream,
    ) {
        var autenticado = false
            private set
        var cid: String? = null
        var nome: String = "Celular"

        fun enviarTexto(texto: String) {
            synchronized(output) {
                try {
                    val bytes = texto.toByteArray(StandardCharsets.UTF_8)
                    output.write(0x81) // FIN + Text frame
                    val len = bytes.size
                    when {
                        len <= 125 -> {
                            output.write(len)
                        }
                        len <= 65535 -> {
                            output.write(126)
                            output.write((len shr 8) and 0xFF)
                            output.write(len and 0xFF)
                        }
                        else -> {
                            output.write(127)
                            for (i in 7 downTo 0) {
                                output.write(((len.toLong() shr (i * 8)) and 0xFF).toInt())
                            }
                        }
                    }
                    output.write(bytes)
                    output.flush()
                } catch (_: Exception) {
                    fechar()
                }
            }
        }

        fun fechar() {
            runCatching { socket.close() }
        }

        fun rodarLoop() {
            try {
                while (ativo && !socket.isClosed) {
                    val b0 = input.read()
                    if (b0 == -1) break
                    val opcode = b0 and 0x0F

                    val b1 = input.read()
                    if (b1 == -1) break
                    val masked = (b1 and 0x80) != 0
                    var payloadLen = (b1 and 0x7F).toLong()

                    if (payloadLen == 126L) {
                        val p0 = input.read()
                        val p1 = input.read()
                        if (p0 == -1 || p1 == -1) break
                        payloadLen = ((p0 shl 8) or p1).toLong()
                    } else if (payloadLen == 127L) {
                        var total = 0L
                        for (i in 0 until 8) {
                            val p = input.read()
                            if (p == -1) break
                            total = (total shl 8) or (p and 0xFF).toLong()
                        }
                        payloadLen = total
                    }

                    val maskKey = ByteArray(4)
                    if (masked) {
                        for (i in 0 until 4) {
                            val m = input.read()
                            if (m == -1) return
                            maskKey[i] = m.toByte()
                        }
                    }

                    if (payloadLen > 1024 * 1024) break // Teto de segurança: 1 MB

                    val payload = ByteArray(payloadLen.toInt())
                    var lidos = 0
                    while (lidos < payload.size) {
                        val r = input.read(payload, lidos, payload.size - lidos)
                        if (r == -1) break
                        lidos += r
                    }

                    if (masked) {
                        for (i in payload.indices) {
                            payload[i] = (payload[i].toInt() xor maskKey[i % 4].toInt()).toByte()
                        }
                    }

                    when (opcode) {
                        0x8 -> { // Fechar
                            break
                        }
                        0x9 -> { // Ping -> responde Pong
                            synchronized(output) {
                                output.write(0x8A) // Opcode 0xA (Pong)
                                output.write(0)
                                output.flush()
                            }
                        }
                        0x1 -> { // Texto JSON
                            val texto = String(payload, StandardCharsets.UTF_8)
                            processarMensagem(texto)
                        }
                    }
                }
            } catch (_: Exception) {
            } finally {
                fechar()
            }
        }

        private fun processarMensagem(texto: String) {
            val json = runCatching { JSONObject(texto) }.getOrNull() ?: return
            val tipo = json.optString("t")

            when (tipo) {
                "hello" -> {
                    val idCelular = json.optString("cid")
                    nome = json.optString("name", "Celular")
                    val pin = json.optString("pin")
                    cid = idCelular

                    // Conexão automática igual ao YouTube Cast!
                    // Dispositivos na mesma rede Wi-Fi / LAN conectam direto sem travar a tela
                    val ehLocal = socket.inetAddress.isSiteLocalAddress || socket.inetAddress.isLoopbackAddress
                    val jaConfiado = ehCidConfiavel(idCelular)
                    val pinCorreto = pin.isNotEmpty() && pin == pinAtual

                    if (ehLocal || jaConfiado || pinCorreto) {
                        confiarCid(context, idCelular)
                        darBoasVindas()
                        handler.post {
                            Toast.makeText(context, "📱 Saimo Connect: $nome conectado", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        enviarTexto(JSONObject().apply { put("t", "pending") }.toString())
                        solicitarAprovacaoNaUi(nome) { permitido ->
                            if (permitido) {
                                confiarCid(context, idCelular)
                                darBoasVindas()
                            } else {
                                enviarTexto(JSONObject().apply { put("t", "denied") }.toString())
                                fechar()
                            }
                        }
                    }
                }
                "ping" -> {
                    enviarTexto(JSONObject().apply { put("t", "pong") }.toString())
                }
                else -> {
                    if (!autenticado) return
                    executarComando(tipo, json)
                }
            }
        }

        private fun darBoasVindas() {
            autenticado = true
            val welcome = JSONObject().apply {
                put("t", "welcome")
                put("tv", JSONObject().apply {
                    put("id", tvId)
                    put("name", nomeDaTv())
                })
            }.toString()
            enviarTexto(welcome)
            notificarMudancaDeEstado()
        }

        private fun executarComando(tipo: String, json: JSONObject) {
            handler.post {
                val lis = listener
                when (tipo) {
                    "tune" -> {
                        val canal = json.optString("channel")
                        val screen = if (json.has("screen")) json.optInt("screen") else null
                        if (lis != null) {
                            lis.sintonizarCanal(canal, screen)
                        } else {
                            val act = SaimoApp.atividadeAtual ?: context
                            val intent = Intent(act, MainActivity::class.java).apply {
                                putExtra(MainActivity.EXTRA_CANAL, canal)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            }
                            act.startActivity(intent)
                        }
                    }
                    "multi" -> {
                        val on = json.optBoolean("on", false)
                        if (lis != null) {
                            lis.alternarMultiTela(on)
                        } else {
                            val act = SaimoApp.atividadeAtual ?: context
                            val intent = Intent(act, MainActivity::class.java).apply {
                                putExtra("EXTRA_MULTI", on)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            }
                            act.startActivity(intent)
                        }
                    }
                    "activeScreen" -> {
                        val idx = json.optInt("idx", 0)
                        lis?.definirMetadeAtiva(idx)
                    }
                    "tvAudio" -> {
                        val src = json.optString("src", "0")
                        tvAudio = src
                        lis?.definirTvAudio(src)
                        notificarMudancaDeEstado()
                    }
                    "playVod" -> {
                        val key = json.optString("key")
                        val temp = if (json.has("temporada")) json.optInt("temporada") else null
                        val ep = if (json.has("episodio")) json.optInt("episodio") else null
                        val fonte = if (json.has("fonte")) json.optInt("fonte") else null
                        if (lis != null) {
                            lis.abrirVod(key, temp, ep, fonte)
                        } else {
                            val act = SaimoApp.atividadeAtual ?: context
                            CoroutineScope(Dispatchers.Main).launch {
                                val achado = Vod.buscar(act, key, 5).firstOrNull()
                                if (achado != null) {
                                    val alvo = Alvo(achado.titulo, achado.serie, achado.letra, achado.ano)
                                    PlayerActivity.abrir(act, alvo, temp ?: 0, ep ?: 0)
                                } else {
                                    val alvo = Alvo(key, (temp ?: 0) > 0, "", "")
                                    PlayerActivity.abrir(act, alvo, temp ?: 0, ep ?: 0)
                                }
                            }
                        }
                    }
                    "playPause" -> lis?.pausarOuRetomarVod()
                    "seek" -> {
                        val ms = json.optLong("ms", -1L)
                        val delta = if (json.has("delta")) json.optLong("delta") else null
                        lis?.avancarOuVoltarVod(ms, delta)
                    }
                    "fonte" -> {
                        val delta = json.optInt("delta", 1)
                        lis?.trocarFonte(delta)
                    }
                    "key" -> {
                        val code = json.optString("code")
                        val keyEvent = when (code) {
                            "UP" -> KeyEvent.KEYCODE_DPAD_UP
                            "DOWN" -> KeyEvent.KEYCODE_DPAD_DOWN
                            "LEFT" -> KeyEvent.KEYCODE_DPAD_LEFT
                            "RIGHT" -> KeyEvent.KEYCODE_DPAD_RIGHT
                            "OK" -> KeyEvent.KEYCODE_DPAD_CENTER
                            "BACK" -> KeyEvent.KEYCODE_BACK
                            "HOME" -> KeyEvent.KEYCODE_HOME
                            "MENU" -> KeyEvent.KEYCODE_MENU
                            "CH_UP" -> KeyEvent.KEYCODE_CHANNEL_UP
                            "CH_DOWN" -> KeyEvent.KEYCODE_CHANNEL_DOWN
                            else -> 0
                        }
                        if (code == "HOME") {
                            val act = SaimoApp.atividadeAtual ?: context
                            if (act !is EscolhaActivity) {
                                val intent = Intent(act, EscolhaActivity::class.java).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                }
                                act.startActivity(intent)
                            }
                        } else if (keyEvent != 0) {
                            if (lis != null) {
                                lis.simularTecla(keyEvent)
                            } else {
                                val act = SaimoApp.atividadeAtual
                                if (act != null) {
                                    val now = SystemClock.uptimeMillis()
                                    val down = KeyEvent(now, now, KeyEvent.ACTION_DOWN, keyEvent, 0)
                                    val up = KeyEvent(now, now + 10, KeyEvent.ACTION_UP, keyEvent, 0)
                                    act.dispatchKeyEvent(down)
                                    act.dispatchKeyEvent(up)
                                }
                            }
                        }
                    }
                    "volume" -> {
                        val delta = if (json.has("delta")) json.optInt("delta") else null
                        val set = if (json.has("set")) json.optDouble("set").toFloat() else null
                        val mute = if (json.has("mute")) json.optBoolean("mute") else null
                        if (lis != null) {
                            lis.ajustarVolume(delta, set, mute)
                        } else {
                            val audioMgr = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
                            if (audioMgr != null) {
                                if (delta != null) {
                                    val dir = if (delta > 0) AudioManager.ADJUST_RAISE else AudioManager.ADJUST_LOWER
                                    audioMgr.adjustStreamVolume(AudioManager.STREAM_MUSIC, dir, AudioManager.FLAG_SHOW_UI)
                                } else if (set != null) {
                                    val maxVol = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                                    val novoVol = (set * maxVol).toInt().coerceIn(0, maxVol)
                                    audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, novoVol, AudioManager.FLAG_SHOW_UI)
                                } else if (mute != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    val dir = if (mute) AudioManager.ADJUST_MUTE else AudioManager.ADJUST_UNMUTE
                                    audioMgr.adjustStreamVolume(AudioManager.STREAM_MUSIC, dir, AudioManager.FLAG_SHOW_UI)
                                }
                            }
                            notificarMudancaDeEstado()
                        }
                    }
                    "text" -> {
                        val valor = json.optString("value", "")
                        lis?.preencherTexto(valor)
                    }
                    "phoneAudio" -> {
                        val id = cid ?: return@post
                        val src = json.optString("src", "none")
                        phoneAudioMap[id] = PhoneAudioStatus(id, nome, src)
                        lis?.onPhoneAudioChanged(phoneAudioMap.values.toList())
                        notificarMudancaDeEstado()
                    }
                }
            }
        }
    }
}
