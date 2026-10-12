# Mantém os nomes que aparecem nas pilhas de erro enviadas ao Saimo Monitor.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
# OkHttp e Coil trazem as próprias regras; estas silenciam avisos de
# dependências opcionais que o app não usa.
-dontwarn org.bouncycastle.**
-dontwarn org.conscrypt.**
-dontwarn org.openjsse.**
# Log de depuração não vai para o APK publicado: cada um custava montar a
# string a cada troca de canal, para ninguém ler. Avisos e erros ficam.
-assumenosideeffects class android.util.Log {
    public static int d(...);
    public static int v(...);
}
