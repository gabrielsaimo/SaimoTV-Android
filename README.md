# Saimo TV — Android TV / Google TV

Canais ao vivo com guia de programação, filmes, séries, animes e doramas, feitos
para TV Box e controle remoto. Funciona do **Android 6** em diante (quem ainda
tem Android 5 fica na 2.0.2).

O APK sai no mesmo release dos outros apps:
<https://github.com/gabrielsaimo/SaimoPlayer/releases/latest>. O app procura
versão nova sozinho e oferece a atualização na tela.

## O que tem

**Navegação**
- Menu do topo em todas as telas — Início, Ao vivo, Filmes, Séries, Animes,
  Doramas, Favoritos, Buscar e Ajustes. Seta para cima chega nele de qualquer
  lugar; VOLTAR sempre leva à tela inicial.
- Tela inicial em fileiras: continuar assistindo, destaques, lançamentos,
  gêneros e canais no ar. O destaque do topo acompanha o cartão em foco.
- Páginas de Filmes/Séries/Animes/Doramas com fileiras e um "Explorar" de A a Z
  com filtro de gênero e ordenação.
- Busca única (canais, filmes, séries, atores) com teclado na tela, voz,
  tolerância a erro de digitação e histórico.
- Ícones Material em tudo (nada de emoji, que vira quadrado em box antigo).
- Carrosséis estáveis: a capa não cresce no foco (o contorno ciano mostra onde
  está), andar para o lado nunca rola a tela, a seta para na ponta da fileira
  e só cima/baixo trocam de fileira — a fileira nova sobe sempre para o mesmo
  lugar. Capas com canto arredondado; logo PNG de canal sem a letra atrás
  (`Cartoes.kt`, `Inicio.Filas`).

**Canais ao vivo**
- Lista com seções (TV Aberta, Esportes, Notícias…), prévia do programa do canal
  em foco e números fixos por canal.
- Segurar a seta acelera (1, 3, 10 canais por passo); CH+/CH− na lista pula de
  seção; o app abre no último canal e LAST volta ao anterior.
- Guia de programação, lembretes de programa e timer de sono.
- Troca de fonte automática quando uma cai, e manual pela lista.
- Ao vivo não pausa: PLAY/PAUSE do controle e do Assistant não param o canal.

**Filmes e séries**
- Ficha com sinopse, elenco, temporadas e episódios, e "Mais como este".
- Player próprio: pula abertura e recapitulação com os tempos do
  [TheIntroDB](https://theintrodb.org), cartão do próximo episódio nos créditos,
  "ainda está assistindo?", continuar de onde parou.
- Botão **Fonte N**: escolhe entre todas as fontes (versão e servidor) sem
  perder o ponto. Fonte que não abre em 15 s, ou que "termina" nos primeiros
  segundos, cai para a seguinte sozinha.
- Áudio, legendas (tamanho, fundo, idioma) e qualidade. Filmes e séries ganham
  legendas do OpenSubtitles (pt-BR, pt-PT, inglês, espanhol) com ajuste de
  sincronia, no mesmo painel — sem chave nem cadastro.

**Google TV**
- "Continuar assistindo" do sistema (Watch Next), canal do app na tela inicial e
  busca do sistema. Links `saimo://assistir`, `saimo://titulo` e `saimo://canal`.
  No Google TV mais novo o sistema só mostra isso para apps parceiros.

**Nada de terceiros**: nenhum recurso depende de outro app instalado (o trailer
via YouTube saiu na 2.0 por isso).

## Controle

Ao vivo:

| Tecla | Ação |
|---|---|
| OK | mostra a programação do canal (rodapé) |
| OK com o rodapé na tela | escolhe a fonte |
| segurar OK, ou MENU | opções: áudio, fontes, favorito, timer |
| ↑ / ↓, CH+ / CH− | canal anterior / seguinte |
| ← | lista de canais; dentro dela, as seções |
| →, GUIA | programação; dentro da lista, as fontes do canal |
| LAST | volta ao canal anterior |
| 0–9 | número do canal |
| VOLTAR | fecha a lista |
| PLAY/PAUSE | nada: ao vivo sempre toca |

Filmes e séries: ← / → avançam e voltam (segurar acelera), OK mostra os
controles, ↓ leva aos botões, VOLTAR sai guardando o ponto.

O controle da Xiaomi não tem MENU nem números: tudo que depende deles tem
caminho alternativo (segurar OK, teclado na tela).

## Leve em aparelho de 1 GB

Um TV Box de 1 GB mata o app sem aviso quando a memória aperta. O que foi feito
para isso não acontecer:

- Guia XMLTV lido em fluxo, sem expressão regular por programa; o regex do
  Android copia a entrada para memória nativa, e isso derrubava o canal no ar.
- Cache do guia lido e gravado em fluxo (`JsonReader`/`JsonWriter`); em
  aparelho fraco a grade cobre 30 h e os feeds só completam canais sem guia.
- Buffer do vídeo com teto em bytes, cache de imagens de tamanho fixo e
  bitmaps RGB_565 em aparelho fraco.
- Arquivo de gêneros e índice de busca compactos, lidos em fluxo.

Detecção de "pouca memória" em `Aparelho.kt` (isLowRamDevice ou até 1,5 GB).

## Estrutura

| Arquivo | Papel |
|---|---|
| `EscolhaActivity` | tela inicial (fileiras) |
| `MainActivity` | TV ao vivo |
| `PlayerActivity` | player de filmes e séries |
| `FichaActivity`, `AtorActivity` | ficha do título e do ator |
| `PaginaActivity`, `GradeActivity` | páginas por tipo e grade A–Z |
| `BuscaActivity`, `BuscaDoSistema` | busca do app e do Google TV |
| `GuideActivity`, `Lembretes` | guia e lembretes |
| `AjustesActivity`, `Preferencias` | ajustes |
| `MenuGlobal` | menu do topo e fundo, herdados por `TelaComMenu` |
| `Epg`, `MeuGuia`, `GuiaDeTv` | guia de programação |
| `Vod`, `Titulos`, `Generos`, `Progresso` | acervo, resolução de título, fichas e progresso |
| `Pulos` | TheIntroDB |
| `Legendas` | OpenSubtitles pelo id do TMDB (via `vod/imdb/`) |
| `ProximaNaTv`, `CanalNaTv`, `Links` | integração com o Google TV |
| `Remote`, `Catalog`, `FontesDesativadas` | lista de canais publicada |
| `Telemetria` | contagem anônima de uso (desligável nos Ajustes) |

## Versões

Build com Gradle 9.8, Android Gradle Plugin 9.4 (Kotlin embutido, 2.4) e
Android 37. Player Media3 1.11, imagens Coil 3.6, rede OkHttp 5.5 com
DNS-over-HTTPS.

## Compilar

SDK, Gradle e emuladores ficam no SSD
(`/Volumes/SSD 1TB/DEV/AndroidDev`, com links em `~/Library/Android/sdk`).

```bash
export ANDROID_HOME="/Volumes/SSD 1TB/DEV/AndroidDev/sdk" JAVA_HOME=/opt/homebrew/opt/openjdk@17
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

O APK de release (R8, recursos enxugados, assinado com a chave da pasta
`chaves`) é montado pelo `release.sh` do SaimoPlayer, que publica todos os apps
juntos.

## Testes

```bash
gradle testDebugUnitTest
```

## Verificar

Cada mudança é conferida em emulador, não só compilada:

- **SaimoTV** — Google TV, API 34, 2 GB.
- **SaimoATV** — Android TV clássico, API 31, 1 GB: o "TV Box fraco". Sem swap,
  ele é mais severo que um aparelho real; app pesado de terceiros (YouTube,
  WebView) morre ali.

```bash
emulator -avd SaimoATV -no-snapshot-save
adb shell am start -a android.intent.action.VIEW -d "saimo://canal?n=Band"
```

## Canais e ícones

```bash
python3 scripts/gen_catalog.py   # lista embutida de reserva
python3 scripts/gen_icons.py     # ícone e banner a partir da arte do Mac
```
