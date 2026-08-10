#!/usr/bin/env python3
"""Gera o icone e o banner do Android a partir do icone do app de macOS.

Uma fonte so — SaimoPlayer/AppIcon.png — para as duas plataformas nao
divergirem quando o icone mudar.
"""
from pathlib import Path

from PIL import Image

ROOT = Path(__file__).resolve().parent.parent
SOURCE = ROOT.parent / "SaimoPlayer" / "AppIcon.png"
RES = ROOT / "app" / "src" / "main" / "res"

# Densidades legadas: o minSdk é 21, então o icone adaptativo sozinho nao basta.
DENSITIES = {"mdpi": 48, "hdpi": 72, "xhdpi": 96, "xxhdpi": 144, "xxxhdpi": 192}

# O cartao branco do icone — logo mais a palavra "Saimo TV" — dentro da arte de
# 1024. É a parte que continua legivel do outro lado da sala.
CARD = (140, 110, 912, 912)


def main() -> None:
    art = Image.open(SOURCE).convert("RGBA")

    for density, size in DENSITIES.items():
        folder = RES / f"mipmap-{density}"
        folder.mkdir(parents=True, exist_ok=True)
        icon = art.resize((size, size), Image.LANCZOS)
        icon.save(folder / "ic_launcher.png")
        icon.save(folder / "ic_launcher_round.png")

    # Banner do Android TV: 16:9, com o cartao branco sobre um degrade feito das
    # cores dos proprios cantos da arte — esticar a arte inteira deixaria uma
    # segunda copia do cartao fantasma atras.
    left = art.crop((0, 0, 120, 1024)).resize((1, 1), Image.LANCZOS).getpixel((0, 0))
    right = art.crop((904, 0, 1024, 1024)).resize((1, 1), Image.LANCZOS).getpixel((0, 0))
    banner = Image.new("RGBA", (320, 180))
    pixels = banner.load()
    for x in range(320):
        ratio = x / 319
        colour = tuple(round(a + (b - a) * ratio) for a, b in zip(left[:3], right[:3])) + (255,)
        for y in range(180):
            pixels[x, y] = colour
    card = art.crop(CARD)
    height = 140
    width = round(card.width * height / card.height)
    card = card.resize((width, height), Image.LANCZOS)
    banner.alpha_composite(card, ((320 - width) // 2, (180 - height) // 2))
    (RES / "drawable-xhdpi").mkdir(parents=True, exist_ok=True)
    banner.convert("RGB").save(RES / "drawable-xhdpi" / "banner.png")
    legacy = RES / "drawable" / "banner.png"
    if legacy.exists():
        legacy.unlink()

    print("icones gerados")


if __name__ == "__main__":
    main()
