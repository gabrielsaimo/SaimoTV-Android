#!/usr/bin/env python3
"""Atualiza a lista de canais de reserva que vai dentro do APK.

A reserva é o próprio catálogo publicado (SaimoPlayer/catalogo.txt e
restritos.txt), copiado para app/src/main/resources/reserva/. O app a lê com
o mesmo parser da lista baixada, só quando precisa: é com ela que ele abre na
primeira vez, antes de a lista publicada chegar.

Até a 2.1.3 este script gerava Catalog.kt com cada canal escrito em Kotlin —
uns 0,6 MB de código compilado e uma classe gigante carregada na abertura.

    python3 scripts/gen_catalog.py
"""
import shutil
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
PUBLICADO = ROOT.parent / "SaimoPlayer"
DESTINO = ROOT / "app" / "src" / "main" / "resources" / "reserva"


def main() -> None:
    DESTINO.mkdir(parents=True, exist_ok=True)
    for nome in ("catalogo.txt", "restritos.txt"):
        origem = PUBLICADO / nome
        if not origem.exists():
            raise SystemExit(f"falta {origem}")
        shutil.copyfile(origem, DESTINO / nome)
        canais = sum(1 for linha in origem.read_text(encoding="utf-8").splitlines() if linha.startswith("canal:"))
        print(f"{nome}: {canais} canais")


if __name__ == "__main__":
    main()
