import re

with open("app/src/main/AndroidManifest.xml", "r") as f:
    text = f.read()

if "EventosActivity" not in text:
    text = text.replace(
        "</application>",
        '    <activity android:name=".EventosActivity" />\n    </application>'
    )
    with open("app/src/main/AndroidManifest.xml", "w") as f:
        f.write(text)
