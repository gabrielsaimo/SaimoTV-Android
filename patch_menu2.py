import re

with open("app/src/main/java/br/com/saimo/tv/MenuGlobal.kt", "r") as f:
    text = f.read()

text = text.replace(
    "Triple(Aba.AO_VIVO, R.string.menu_ao_vivo, R.drawable.ic_live_tv),",
    "Triple(Aba.AO_VIVO, R.string.menu_ao_vivo, R.drawable.ic_live_tv),\n            Triple(Aba.EVENTOS, R.string.menu_eventos, 0),"
)

with open("app/src/main/java/br/com/saimo/tv/MenuGlobal.kt", "w") as f:
    f.write(text)
