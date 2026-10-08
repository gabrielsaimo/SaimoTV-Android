import re

with open("app/src/main/java/br/com/saimo/tv/EventosActivity.kt", "r") as f:
    text = f.read()

logic = """
                    for (i in 0 until json.length()) {
                        val obj = json.getJSONObject(i)
                        
                        val inicioStr = obj.getString("time_start")
                        val fimStr = obj.getString("time_end")
                        
                        var fimMs = 0L
                        var inicioMs = 0L
                        try {
                            val formatoData = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", java.util.Locale.US)
                            val dataFim = formatoData.parse(fimStr)
                            val dataInicio = formatoData.parse(inicioStr)
                            if (dataFim != null) fimMs = dataFim.time
                            if (dataInicio != null) inicioMs = dataInicio.time
                        } catch (e: Exception) { }
                        
                        if (fimMs == 0L && inicioMs > 0L) {
                            fimMs = inicioMs + (2 * 60 * 60 * 1000)
                        }
                        
                        if (fimMs > 0 && System.currentTimeMillis() > fimMs) {
                            continue
                        }

                        val league = obj.getJSONObject("league")
                        val teams = obj.getJSONObject("teams")
                        val home = teams.getJSONObject("home")
                        val away = teams.getJSONObject("away")
                        val players = obj.optJSONArray("players")
                        val playerUrl = if (players != null && players.length() > 0) players.getString(0) else ""
                        
                        var horarioFormatado = ""
                        try {
                            val sdfHorario = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                            if (inicioMs > 0L) horarioFormatado = sdfHorario.format(java.util.Date(inicioMs))
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
                            playerUrl = playerUrl,
                            horarioFormatado = horarioFormatado
                        ))
                    }
"""

# Replace the entire loop
text = re.sub(
    r'for \(i in 0 until json\.length\(\)\) \{.*?\}\s*withContext',
    logic.strip() + "\n                    withContext",
    text,
    flags=re.DOTALL
)

with open("app/src/main/java/br/com/saimo/tv/EventosActivity.kt", "w") as f:
    f.write(text)
