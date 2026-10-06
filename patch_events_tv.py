import re

with open("app/src/main/java/br/com/saimo/tv/EventosActivity.kt", "r") as f:
    text = f.read()

# Add imports
text = text.replace(
    "import android.widget.TextView",
    "import android.widget.TextView\nimport android.widget.Toast\nimport coil3.load"
)

# Update ViewHolder
text = text.replace(
    "val timeFora: TextView = v.findViewById(R.id.eventoTimeFora)",
    "val timeFora: TextView = v.findViewById(R.id.eventoTimeFora)\n        val imgLiga: ImageView = v.findViewById(R.id.imgLiga)\n        val imgTimeCasa: ImageView = v.findViewById(R.id.imgTimeCasa)\n        val imgTimeFora: ImageView = v.findViewById(R.id.imgTimeFora)"
)

# Update bind view holder
bind_logic = """
            if (ev.ligaLogo.isNotEmpty()) holder.imgLiga.load(ev.ligaLogo)
            if (ev.timeCasaLogo.isNotEmpty()) holder.imgTimeCasa.load(ev.timeCasaLogo)
            if (ev.timeForaLogo.isNotEmpty()) holder.imgTimeFora.load(ev.timeForaLogo)
            
            holder.itemView.setOnClickListener {
                if (ev.playerUrl.isNotEmpty()) {
                    val slug = ev.playerUrl.substringAfterLast("/").lowercase()
                    val slugLimpo = slug.replace("-", "").replace(" ", "")
                    val channel = CATALOG.firstOrNull { it.name.lowercase().replace(" ", "") == slugLimpo }
                        ?: CATALOG.firstOrNull { it.name.lowercase().contains(slugLimpo) }
                    
                    if (channel != null) {
                        val i = Intent(this@EventosActivity, MainActivity::class.java)
                        i.putExtra(MainActivity.EXTRA_CANAL, channel.name)
                        startActivity(i)
                    } else {
                        Toast.makeText(this@EventosActivity, "Canal do evento não encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
"""
text = re.sub(
    r'// Simplesmente define os nomes por hora.*?holder\.itemView\.setOnClickListener \{.*?\}[\s\S]*?\}',
    bind_logic.strip() + "\n        }",
    text,
    flags=re.DOTALL
)

with open("app/src/main/java/br/com/saimo/tv/EventosActivity.kt", "w") as f:
    f.write(text)
