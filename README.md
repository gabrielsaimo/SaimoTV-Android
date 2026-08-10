# Saimo TV — Android TV / Google TV

Os mesmos 68 canais e o mesmo guia do app de macOS, refeitos para televisão e
controle remoto.

## A diferença estrutural

O app de macOS precisa de um proxy HTTP local e de um ffmpeg embutido, porque o
AVFoundation recusa DASH por completo e só decodifica HEVC dentro de fMP4. Nada
disso vale aqui: o ExoPlayer fala DASH com ClearKey e HEVC-em-TS nativamente,
então cada canal toca direto da origem. É por isso que este APK tem 8 MB contra
23 MB do DMG.

DNS filtrado, que no macOS exigiu um resolvedor DoH escrito à mão, aqui são
poucas linhas: o OkHttp já traz DNS-over-HTTPS, e é ele que serve os dados ao
player.

## Controle

| Tecla | Ação |
|---|---|
| OK ou ← | abre a lista de canais |
| ↑ / ↓ ou CH+ / CH− | canal anterior / seguinte, direto na imagem |
| →, GUIA ou INFO | abre a programação |
| VOLTAR | fecha |
| MENU | favorita o canal (favoritos sobem para o topo) |
| 0–9 | digita o número do canal |

Nada fica fixo por cima do vídeo: o cartão do canal aparece a cada tecla e some
sozinho em seis segundos.

A grade do macOS é canais por tempo, o que funciona com ponteiro. Numa TV,
mover um cursor em dois eixos com o D-pad é lento, então a mesma informação foi
dividida: canais à esquerda, programação do canal focado à direita.

## Guia

Idêntico ao do macOS: meuguia.tv como fonte prioritária, os dois feeds XMLTV
preenchendo o resto, casamento em duas passagens com o exato vencendo o difuso,
e o **resultado analisado** no cache — não o XML — para o guia aparecer na hora
no lançamento seguinte.

Três decisões existem por causa do TV Box, que é bem mais fraco que o Mac:

- **XMLTV lido em fluxo.** Um dos feeds tem 15 MB. Transformá-lo em `String`
  para varrer com expressão regular custava dezenas de milhares de
  `Pattern.compile` e dezenas de MB transitórios — no aparelho isso aparecia
  como app lento e guia que nunca chegava. O analisador agora entrega um
  elemento de cada vez e não compila nada no caminho quente: 58 dos 68 canais em
  **1,1 s**, verificado contra os feeds de verdade em `app/src/test`.
- **Publicação em etapas.** O meuguia chega primeiro e já pinta a tela; os feeds
  entram depois. Antes a grade só aparecia quando tudo terminava.
- **O guia espera o vídeo.** Ele começa quando o canal já está tocando, e no
  máximo seis downloads correm ao mesmo tempo, para não roubar a banda do
  primeiro segundo de imagem.

```bash
gradle testDebugUnitTest --tests "br.com.saimo.tv.EpgTest"
```

## Aparência

Fundo quase preto, uma única cor de acento tirada do próprio ícone e tipografia
grande. O ícone e o banner da fileira do Android TV são gerados da mesma arte do
app de macOS, para as duas plataformas não divergirem:

```bash
python3 scripts/gen_icons.py
```

## Canais

Gerados a partir de `SaimoPlayer/Sources/Channels.swift`:

```bash
python3 scripts/gen_catalog.py
```

Cada canal lista suas fontes em ordem de preferência e o player desce para a
próxima quando uma falha. Os 22 canais DASH carregam o par KID/chave do
ClearKey, recuperado da lista de origem — o catálogo do macOS guarda só a chave,
e o ExoPlayer precisa dos dois.

## Compilar

```bash
JAVA_HOME=/opt/homebrew/opt/openjdk@17 gradle assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
