import re

with open("app/src/main/java/br/com/saimo/tv/PlayerActivity.kt", "r") as f:
    text = f.read()

# I need to change fonteAtual to use a local file.
# Replace:
old_code = """        // O arquivo vai embutido (data:): já está baixado, e o atraso escolhido
        // é aplicado nas marcas de tempo antes de entregar ao player.
        val dados = Base64.encodeToString(Legendas.deslocar(srt, legendaAtraso).toByteArray(), Base64.NO_WRAP)
        val configuracao = MediaItem.SubtitleConfiguration.Builder(Uri.parse("data:application/x-subrip;base64,$dados"))"""

new_code = """        // Salva o arquivo no cache para evitar limites de tamanho e bugs de parsing
        // do DataSchemeDataSource do ExoPlayer com base64 gigantes.
        val arquivo = java.io.File(cacheDir, "saimo_legenda.srt")
        arquivo.writeText(Legendas.deslocar(srt, legendaAtraso), Charsets.UTF_8)
        val configuracao = MediaItem.SubtitleConfiguration.Builder(Uri.fromFile(arquivo))"""

text = text.replace(old_code, new_code)

# Did it also import java.io.File? We used java.io.File directly, so it's fine.

with open("app/src/main/java/br/com/saimo/tv/PlayerActivity.kt", "w") as f:
    f.write(text)
