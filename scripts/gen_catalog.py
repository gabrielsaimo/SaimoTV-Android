#!/usr/bin/env python3
"""Gera Catalog.kt a partir do catálogo do app de macOS.

Uma fonte só para as duas plataformas: SaimoPlayer/Sources/Channels.swift, que
guarda o ClearKey como KID:CHAVE. O AVFoundation usa só a chave e o ExoPlayer
precisa das duas metades, então o par mora inteiro no catálogo e cada lado pega
o que lhe serve.
"""
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
SWIFT = ROOT.parent / "SaimoPlayer" / "Sources" / "Channels.swift"
KOTLIN = ROOT / "app" / "src" / "main" / "java" / "br" / "com" / "saimo" / "tv" / "Catalog.kt"

HEADER = '''package br.com.saimo.tv

// Gerado a partir de SaimoPlayer/Sources/Channels.swift — não editar à mão.
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
    /// Seção da lista ("24 Horas", "Esportes"...). Nula no catálogo embutido:
    /// aí a seção sai do nome, ver [Categorias].
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


def parse_swift(declaration):
    text = SWIFT.read_text(encoding="utf-8")
    start = text.index(declaration) + len(declaration)
    text = text[start:text.index("\n]", start)]
    channels = []
    entry = re.compile(
        r'CatalogEntry\(\s*name:\s*(' + STRING + r'),\s*'
        r'logo:\s*(' + STRING + r'),\s*sources:\s*\[(.*?)\n        \]\)', re.S)
    source = re.compile(
        r'Source\(url:\s*(' + STRING + r'),\s*'
        r'(?:referer:\s*(' + STRING + r'),\s*)?'
        r'(?:userAgent:\s*(' + STRING + r'),\s*)?'
        r'(?:clearKey:\s*(' + STRING + r'))?\s*\)', re.S)
    for match in entry.finditer(text):
        sources = []
        for item in source.finditer(match.group(3)):
            sources.append({
                "url": unquote(item.group(1)),
                "referer": unquote(item.group(2) or "nil"),
                "userAgent": unquote(item.group(3) or "nil"),
                "key": unquote(item.group(4) or "nil"),
            })
        if not sources:
            raise SystemExit(f"canal sem fonte reconhecida: {match.group(1)}")
        channels.append({
            "name": unquote(match.group(1)),
            "logo": unquote(match.group(2)),
            "sources": sources,
        })
    return channels


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
        out.append("    ),")


def main():
    out, missing = [HEADER], []

    open_list = "val CATALOG: List<Channel> = listOf("
    restricted_list = "val RESTRICTED: List<Channel> = listOf("

    catalog = parse_swift("private let catalog: [CatalogEntry] = [")
    restricted = parse_swift("private let restrictedCatalog: [CatalogEntry] = [")

    out.append(open_list)
    emit(out, missing, catalog)
    out.append(")")
    out.append("")
    out.append("/// Só entra na lista depois do código. Ver Unlock.")
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
