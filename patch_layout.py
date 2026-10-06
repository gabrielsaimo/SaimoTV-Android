import os

with open("app/src/main/res/layout/item_evento.xml", "w") as f:
    f.write('''<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="16dp"
    android:layout_margin="8dp"
    android:background="@drawable/fundo_card"
    android:focusable="true">

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="center_vertical"
        android:layout_marginBottom="8dp">
        
        <ImageView
            android:id="@+id/imgLiga"
            android:layout_width="24dp"
            android:layout_height="24dp"
            android:layout_marginEnd="8dp"
            android:scaleType="centerInside" />
            
        <TextView
            android:id="@+id/eventoLiga"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="#AAAAAA"
            android:textSize="14sp" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="center"
        android:layout_marginTop="8dp">

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:gravity="center">
            
            <ImageView
                android:id="@+id/imgTimeCasa"
                android:layout_width="48dp"
                android:layout_height="48dp"
                android:layout_marginBottom="4dp"
                android:scaleType="centerInside" />
                
            <TextView
                android:id="@+id/eventoTimeCasa"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textAlignment="center"
                android:textColor="@android:color/white"
                android:textSize="16sp"
                android:textStyle="bold" />
        </LinearLayout>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text=" x "
            android:textColor="#555555"
            android:textSize="18sp"
            android:textStyle="bold"
            android:layout_marginHorizontal="8dp" />

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:gravity="center">
            
            <ImageView
                android:id="@+id/imgTimeFora"
                android:layout_width="48dp"
                android:layout_height="48dp"
                android:layout_marginBottom="4dp"
                android:scaleType="centerInside" />
                
            <TextView
                android:id="@+id/eventoTimeFora"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textAlignment="center"
                android:textColor="@android:color/white"
                android:textSize="16sp"
                android:textStyle="bold" />
        </LinearLayout>
    </LinearLayout>

    <TextView
        android:id="@+id/eventoTitulo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="12dp"
        android:textColor="#888888"
        android:textSize="12sp" />

</LinearLayout>
''')
