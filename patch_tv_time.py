import re

with open("app/src/main/res/layout/item_evento.xml", "r") as f:
    xml = f.read()

# Insert the TextView for time at the top of the LinearLayout
if "eventoHorario" not in xml:
    time_xml = """
    <TextView
        android:id="@+id/eventoHorario"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#333333"
        android:paddingHorizontal="10dp"
        android:paddingVertical="4dp"
        android:layout_gravity="center_horizontal"
        android:textColor="#00FF00"
        android:textStyle="bold"
        android:textSize="14sp"
        android:layout_marginBottom="8dp" />
"""
    xml = xml.replace(
        '<LinearLayout\n        android:layout_width="wrap_content"',
        time_xml.lstrip() + '\n    <LinearLayout\n        android:layout_width="wrap_content"'
    )

with open("app/src/main/res/layout/item_evento.xml", "w") as f:
    f.write(xml)

with open("app/src/main/java/br/com/saimo/tv/EventosActivity.kt", "r") as f:
    kt = f.read()

kt = kt.replace("import android.widget.Toast", "import android.widget.Toast\nimport java.text.SimpleDateFormat\nimport java.util.Locale\nimport java.util.Date")

logic = """
                    val formatoData = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
                    val agora = System.currentTimeMillis()
                    
                    for (i in 0 until json.length()) {
                        val obj = json.getJSONObject(i)
                        
                        val inicioStr = obj.getString("time_start")
                        val fimStr = obj.getString("time_end")
                        
                        var fimMs = 0L
                        var inicioMs = 0L
                        try {
                            val dataFim = formatoData.parse(fimStr)
                            val dataInicio = formatoData.parse(inicioStr)
                            if (dataFim != null) fimMs = dataFim.time
                            if (dataInicio != null) inicioMs = dataInicio.time
                        } catch (e: Exception) { }
                        
                        // Fallback de 2h se o time_end falhar
                        if (fimMs == 0L && inicioMs > 0L) {
                            fimMs = inicioMs + (2 * 60 * 60 * 1000)
                        }
                        
                        // Oculta eventos que já terminaram
                        if (fimMs > 0 && agora > fimMs) {
                            continue
                        }

                        val league = obj.getJSONObject("league")
                        val teams = obj.getJSONObject("teams")
                        val home = teams.getJSONObject("home")
                        val away = teams.getJSONObject("away")
                        val players = obj.optJSONArray("players")
                        val playerUrl = if (players != null && players.length() > 0) players.getString(0) else ""
                        
                        // Formata horario
                        var horarioFormatado = ""
                        try {
                            val sdfHorario = SimpleDateFormat("HH:mm", Locale.getDefault())
                            if (inicioMs > 0L) {
                                horarioFormatado = sdfHorario.format(Date(inicioMs))
                            }
                        } catch (e: Exception) {}
                        
                        eventos.add(Evento(
                            titulo = obj.getString("title"),
                            ligaNome = league.getString("name"),
                            ligaLogo = league.optString("image"),
                            timeCasaNome = home.getString("name"),
                            timeCasaLogo = home.optString("image"),
                            timeForaNome = away.getString("name"),
                            timeForaLogo = away.optString("image"),
                            inicio = inicioStr,
                            fim = fimStr,
                            horarioFormatado = horarioFormatado,
                            playerUrl = playerUrl
                        ))
                    }
"""

kt = re.sub(
    r'for \(i in 0 until json\.length\(\)\) \{.*?eventos\.add\(Evento\(.*?\}\)',
    logic.strip(),
    kt,
    flags=re.DOTALL
)

kt = kt.replace("val timeFora: TextView = v.findViewById(R.id.eventoTimeFora)", "val timeFora: TextView = v.findViewById(R.id.eventoTimeFora)\n        val horario: TextView = v.findViewById(R.id.eventoHorario)")
kt = kt.replace("holder.timeFora.text = ev.timeForaNome", "holder.timeFora.text = ev.timeForaNome\n            holder.horario.text = ev.horarioFormatado")

kt = kt.replace(
    "val playerUrl: String\n    )",
    "val playerUrl: String,\n        val horarioFormatado: String\n    )"
)

with open("app/src/main/java/br/com/saimo/tv/EventosActivity.kt", "w") as f:
    f.write(kt)
