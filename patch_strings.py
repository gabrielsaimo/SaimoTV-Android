import re

with open("app/src/main/res/values/strings.xml", "r") as f:
    text = f.read()

if "menu_eventos" not in text:
    text = text.replace(
        '<string name="menu_ajustes">Ajustes</string>',
        '<string name="menu_ajustes">Ajustes</string>\n    <string name="menu_eventos">Eventos</string>'
    )
    with open("app/src/main/res/values/strings.xml", "w") as f:
        f.write(text)
