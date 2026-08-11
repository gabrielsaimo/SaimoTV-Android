#!/usr/bin/env python3
"""Gera Catalog.kt a partir do catálogo do app de macOS.

Uma fonte só para as duas plataformas: SaimoPlayer/Sources/Channels.swift. O
Swift guarda apenas a chave do ClearKey, porque o AVFoundation não pede o KID —
o ExoPlayer pede os dois, então o KID já presente no Catalog.kt é preservado,
casando pela URL. Editar o par à mão continua valendo; regenerar não o perde.
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
)

val CATALOG: List<Channel> = listOf(
'''

STRING = r'(?:nil|"(?:[^"\\]|\\.)*")'


def unquote(text):
    if text == "nil":
        return None
    return text[1:-1].replace('\\"', '"').replace("\\\\", "\\")


def quote(value):
    escaped = value.replace("\\", "\\\\").replace('"', '\\"').replace("$", "\\$")
    return f'"{escaped}"'


def known_key_ids():
    """URL -> keyId já presente no Catalog.kt, para não perder o par."""
    if not KOTLIN.exists():
        return {}
    text = KOTLIN.read_text(encoding="utf-8")
    pairs = {}
    for block in re.finditer(
        r'url = "((?:[^"\\]|\\.)*)",(.*?)\n            \)', text, re.S):
        key_id = re.search(r'keyId = "([0-9a-fA-F]+)"', block.group(2))
        if key_id:
            pairs[block.group(1).replace('\\"', '"').replace("\\$", "$")] = key_id.group(1)
    return pairs


def parse_swift():
    text = SWIFT.read_text(encoding="utf-8")
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


def main():
    channels = parse_swift()
    key_ids = known_key_ids()
    out = [HEADER]
    missing = []

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
                key_id = key_ids.get(source["url"])
                if key_id:
                    out.append(f'                keyId = {quote(key_id)},')
                else:
                    missing.append(f'{channel["name"]}: {source["url"][:70]}')
                out.append(f'                key = {quote(source["key"])},')
            out.append("            ),")
        out.append("        ),")
        out.append("    ),")

    out.append(")")
    KOTLIN.write_text("\n".join(out) + "\n", encoding="utf-8")

    total = sum(len(c["sources"]) for c in channels)
    print(f"canais: {len(channels)} | fontes: {total} | com ClearKey: {len(key_ids)}")
    for item in missing:
        print(f"  SEM KID (canal DASH não vai tocar no Android): {item}")


if __name__ == "__main__":
    main()
