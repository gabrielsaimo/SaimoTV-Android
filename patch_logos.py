with open("app/src/main/res/layout/item_evento.xml", "r") as f:
    xml = f.read()

# Make league logo slightly bigger
xml = xml.replace('android:id="@+id/imgLiga"\n            android:layout_width="24dp"\n            android:layout_height="24dp"',
                  'android:id="@+id/imgLiga"\n            android:layout_width="32dp"\n            android:layout_height="32dp"')

# Make team logos much bigger (from 48dp to 96dp) and increase bottom margin
xml = xml.replace('android:id="@+id/imgTimeCasa"\n                android:layout_width="48dp"\n                android:layout_height="48dp"\n                android:layout_marginBottom="4dp"',
                  'android:id="@+id/imgTimeCasa"\n                android:layout_width="96dp"\n                android:layout_height="96dp"\n                android:layout_marginBottom="8dp"')

xml = xml.replace('android:id="@+id/imgTimeFora"\n                android:layout_width="48dp"\n                android:layout_height="48dp"\n                android:layout_marginBottom="4dp"',
                  'android:id="@+id/imgTimeFora"\n                android:layout_width="96dp"\n                android:layout_height="96dp"\n                android:layout_marginBottom="8dp"')

with open("app/src/main/res/layout/item_evento.xml", "w") as f:
    f.write(xml)
