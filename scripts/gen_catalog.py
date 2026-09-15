#!/usr/bin/env python3
"""Gera Catalog.kt a partir das listas publicadas pelo SaimoPlayer.

catalogo.txt e restritos.txt são o que os apps baixam; o Catalog.kt é só a
reserva de quando não há rede, então sai deles, com a categoria de cada canal.
O ClearKey vem como KID:CHAVE: o AVFoundation usa só a chave e o ExoPlayer
precisa das duas metades.
"""
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
PUBLICADO = ROOT.parent / "SaimoPlayer"
KOTLIN = ROOT / "app" / "src" / "main" / "java" / "br" / "com" / "saimo" / "tv" / "Catalog.kt"

HEADER = '''package br.com.saimo.tv

// Gerado de SaimoPlayer/catalogo.txt e restritos.txt — não editar à mão.
// Regenerar com scripts/gen_catalog.py para manter Mac e TV Box iguais.

data class Source(
    val url: String,
    val referer: String? = null,
    val userAgent: String? = null,
    /// Par KID:chave do ClearKey, em hexadecimal, para as fontes DASH.
    val keyId: String? = null,
    val key: String? = null,
) {
    val isDash: Boolean get() = url.contains(".mpd", ignoreCase = true)
}

data class Channel(
    val name: String,
    val logo: String? = null,
    val sources: List<Source>,
    /// Seção da lista ("24 Horas", "Esportes"...). Nula numa lista sem
    /// categoria declarada: aí a seção sai do nome, ver [Categorias].
    val categoria: String? = null,
)

'''

STRING = r'(?:nil|"(?:[^"\\]|\\.)*")'


def unquote(text):
    if text == "nil":
        return None
    return text[1:-1].replace('\\"', '"').replace("\\\\", "\\")


def quote(value):
    escaped = value.replace("\\", "\\\\").replace('"', '\\"').replace("$", "\\$")
    return f'"{escaped}"'


def parse_txt(nome, categoria_padrao=None):
    channels, fonte = [], None
    for raw in (PUBLICADO / nome).read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#") or ":" not in line:
            continue
        campo, valor = (x.strip() for x in line.split(":", 1))
        campo = campo.lower()
        if not valor:
            continue
        if campo == "canal":
            channels.append({"name": valor, "logo": None, "sources": [],
                             "categoria": categoria_padrao})
        elif not channels:
            continue
        elif campo == "logo":
            channels[-1]["logo"] = valor
        elif campo == "categoria":
            channels[-1]["categoria"] = valor
        elif campo == "fonte":
            fonte = {"url": valor, "referer": None, "userAgent": None, "key": None}
            channels[-1]["sources"].append(fonte)
        elif fonte is not None and campo in ("referer", "agente", "chave"):
            fonte[{"agente": "userAgent", "chave": "key"}.get(campo, campo)] = valor
    return [c for c in channels if c["sources"]]


def emit(out, missing, channels):
    for channel in channels:
        out.append("    Channel(")
        out.append(f'        name = {quote(channel["name"])},')
        if channel["logo"]:
            out.append(f'        logo = {quote(channel["logo"])},')
        out.append("        sources = listOf(")
        for source in channel["sources"]:
            out.append("            Source(")
            out.append(f'                url = {quote(source["url"])},')
            if source["referer"]:
                out.append(f'                referer = {quote(source["referer"])},')
            if source["userAgent"]:
                out.append(f'                userAgent = {quote(source["userAgent"])},')
            if source["key"]:
                # O catálogo guarda KID:CHAVE; o ExoPlayer precisa dos dois.
                parts = source["key"].split(":")
                if len(parts) == 2 and all(parts):
                    out.append(f'                keyId = {quote(parts[0])},')
                    out.append(f'                key = {quote(parts[1])},')
                else:
                    missing.append(f'{channel["name"]}: {source["url"][:70]}')
            out.append("            ),")
        out.append("        ),")
        if channel.get("categoria"):
            out.append(f'        categoria = {quote(channel["categoria"])},')
        out.append("    ),")


def main():
    out, missing = [HEADER], []

    open_list = "val CATALOG: List<Channel> = listOf("
    restricted_list = "val RESTRICTED: List<Channel> = listOf("

    catalog = parse_txt("catalogo.txt")
    restricted = parse_txt("restritos.txt", categoria_padrao="Adulto")

    out.append(open_list)
    emit(out, missing, catalog)
    out.append(")")
    out.append("")
    out.append("/// Só entra na lista depois do código, e só sem rede: a que vale é a")
    out.append("/// baixada pelo Remote. Ver Unlock.")
    out.append(restricted_list)
    emit(out, missing, restricted)
    out.append(")")

    KOTLIN.write_text("\n".join(out) + "\n", encoding="utf-8")

    total = sum(len(c["sources"]) for c in catalog)
    com_chave = sum(1 for c in catalog for s in c["sources"] if s["key"])
    print(f"canais: {len(catalog)} | fontes: {total} | com ClearKey: {com_chave}"
          f" | reservados: {len(restricted)}")
    for item in missing:
        print(f"  SEM KID (canal DASH não vai tocar no Android): {item}")


if __name__ == "__main__":
    main()
