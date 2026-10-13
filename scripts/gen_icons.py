#!/usr/bin/env python3
"""Gera o icone e o banner do Android a partir do icone do app de macOS.

Uma fonte so — SaimoPlayer/AppIcon.png — para as duas plataformas nao
divergirem quando o icone mudar.

O Fire TV mostra o icone de app instalado por fora da loja num quadro mais
largo que o quadrado e corta o que passa da borda; a arte do Mac, com o cartao
branco quase ate a borda, saia cortada dos lados. Agora o cartao fica no meio,
com folga, sobre um fundo feito da propria arte desfocada: o que o launcher
cortar e so fundo. Igual no icone em camadas (Android 8+), no icone legado e
no banner das TVs.
"""
from pathlib import Path

from PIL import Image, ImageDraw, ImageFilter

ROOT = Path(__file__).resolve().parent.parent
SOURCE = ROOT.parent / "SaimoPlayer" / "AppIcon.png"
RES = ROOT / "app" / "src" / "main" / "res"

# Densidades legadas: o minSdk é 23, então o icone em camadas sozinho nao basta.
DENSITIES = {"mdpi": 48, "hdpi": 72, "xhdpi": 96, "xxhdpi": 144, "xxxhdpi": 192}
# Banner 16:9 em mais de uma densidade: TV 4K pede o maior.
BANNERS = {"xhdpi": 320, "xxhdpi": 480, "xxxhdpi": 640}

# Quanto do lado menor o cartao ocupa em cada peca.
LEGADO = 0.62
CAMADA = 0.56  # da camada de 108dp; a area segura e 66dp
BANNER = 0.74


def cartao(art: Image.Image) -> tuple[Image.Image, tuple[int, int, int, int]]:
    """O cartao branco (logo e "Saimo TV"), achado pelo claro no meio da arte."""
    cinza = art.convert("L")
    w, h = art.size
    linha = [cinza.getpixel((x, h // 2)) for x in range(w)]
    coluna = [cinza.getpixel((w // 2, y)) for y in range(h)]
    x0 = next(i for i, v in enumerate(linha) if v > 200)
    x1 = w - next(i for i, v in enumerate(reversed(linha)) if v > 200)
    y0 = next(i for i, v in enumerate(coluna) if v > 200)
    y1 = h - next(i for i, v in enumerate(reversed(coluna)) if v > 200)
    card = art.crop((x0, y0, x1, y1))
    # Cantos arredondados com 4x de resolucao para a borda sair lisa.
    mascara = Image.new("L", (card.width * 4, card.height * 4), 0)
    ImageDraw.Draw(mascara).rounded_rectangle(
        (0, 0, card.width * 4 - 1, card.height * 4 - 1), radius=round(card.width * 0.76), fill=255)
    card.putalpha(mascara.resize(card.size, Image.LANCZOS))
    return card, (x0, y0, x1, y1)


def fundo(art: Image.Image, caixa: tuple[int, int, int, int]) -> Image.Image:
    """As cores da arte sem o cartao, desfocadas ate virar degrade."""
    x0, y0, x1, y1 = caixa
    sem = art.copy()
    ImageDraw.Draw(sem).rectangle((x0 - 60, y0 - 60, x1 + 60, y1 + 60), fill=(30, 30, 70, 255))
    return sem.filter(ImageFilter.GaussianBlur(160)).convert("RGBA")


def compor(base: Image.Image, card: Image.Image, w: int, h: int, fracao: float) -> Image.Image:
    img = base.resize((w, h), Image.LANCZOS)
    lado = round(min(w, h) * fracao)
    img.alpha_composite(card.resize((lado, lado), Image.LANCZOS), ((w - lado) // 2, (h - lado) // 2))
    return img.convert("RGB")


def main() -> None:
    art = Image.open(SOURCE).convert("RGBA")
    card, caixa = cartao(art)
    base = fundo(art, caixa)

    for density, size in DENSITIES.items():
        folder = RES / f"mipmap-{density}"
        folder.mkdir(parents=True, exist_ok=True)
        icon = compor(base, card, size, size, LEGADO)
        icon.save(folder / "ic_launcher.png")
        icon.save(folder / "ic_launcher_round.png")
        # Camadas de 108dp do icone adaptativo (mipmap-anydpi-v26).
        camada = round(size * 108 / 48)
        frente = Image.new("RGBA", (camada, camada), (0, 0, 0, 0))
        lado = round(camada * CAMADA)
        frente.alpha_composite(card.resize((lado, lado), Image.LANCZOS),
                               ((camada - lado) // 2, (camada - lado) // 2))
        frente.save(folder / "ic_launcher_frente.png")
        base.resize((camada, camada), Image.LANCZOS).convert("RGB").save(folder / "ic_launcher_fundo.png")

    for density, largura in BANNERS.items():
        folder = RES / f"drawable-{density}"
        folder.mkdir(parents=True, exist_ok=True)
        compor(base, card, largura, largura * 9 // 16, BANNER).save(folder / "banner.png")

    print("icones gerados")


if __name__ == "__main__":
    main()
