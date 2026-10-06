import re

with open("app/src/main/java/br/com/saimo/tv/MenuGlobal.kt", "r") as f:
    text = f.read()

# Add to Aba enum
text = text.replace(
    "enum class Aba { INICIO, AO_VIVO, FILMES, SERIES, ANIMES, DORAMAS, FAVORITOS, EXTRAS, BUSCAR, AJUSTES, NENHUMA }",
    "enum class Aba { INICIO, AO_VIVO, EVENTOS, FILMES, SERIES, ANIMES, DORAMAS, FAVORITOS, EXTRAS, BUSCAR, AJUSTES, NENHUMA }"
)

# Add to menu list
text = text.replace(
    "Triple(Aba.AO_VIVO, R.string.menu_tv, R.drawable.ic_tv),",
    "Triple(Aba.AO_VIVO, R.string.menu_tv, R.drawable.ic_tv),\n            Triple(Aba.EVENTOS, R.string.menu_eventos, 0),"
)

# Add to routing
if "EventosActivity::class.java" not in text:
    text = text.replace(
        "Aba.AO_VIVO -> Intent(tela, MainActivity::class.java)",
        "Aba.AO_VIVO -> Intent(tela, MainActivity::class.java)\n            Aba.EVENTOS -> Intent(tela, EventosActivity::class.java)"
    )

with open("app/src/main/java/br/com/saimo/tv/MenuGlobal.kt", "w") as f:
    f.write(text)
