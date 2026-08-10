# Saimo TV — Android TV / Google TV

Os mesmos 68 canais e o mesmo guia do app de macOS, refeitos para televisão e
controle remoto.

## A diferença estrutural

O app de macOS precisa de um proxy HTTP local e de um ffmpeg embutido, porque o
AVFoundation recusa DASH por completo e só decodifica HEVC dentro de fMP4. Nada
disso vale aqui: o ExoPlayer fala DASH com ClearKey e HEVC-em-TS nativamente,
então cada canal toca direto da origem. É por isso que este APK tem 10 MB contra
23 MB do DMG.

DNS filtrado, que no macOS exigiu um resolvedor DoH escrito à mão, aqui são
poucas linhas: o OkHttp já traz DNS-over-HTTPS, e é ele que serve os dados ao
player.

## Controle

| Tecla | Ação |
|---|---|
| OK ou ← | abre a lista de canais |
| ↓, GUIA ou INFO | abre a programação |
| VOLTAR | fecha |
| CH+ / CH− | troca de canal direto |
| MENU | favorita o canal (favoritos sobem para o topo) |
| 0–9 | digita o número do canal |

A grade do macOS é canais por tempo, o que funciona com ponteiro. Numa TV,
mover um cursor em dois eixos com o D-pad é lento, então a mesma informação foi
dividida: canais à esquerda, programação do canal focado à direita.

## Guia

Idêntico ao do macOS: meuguia.tv como fonte prioritária, os dois feeds XMLTV
preenchendo o resto, casamento em duas passagens com o exato vencendo o difuso,
e o **resultado analisado** no cache — não o XML — para o guia aparecer na hora
no lançamento seguinte.

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
