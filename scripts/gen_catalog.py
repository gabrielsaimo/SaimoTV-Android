#!/usr/bin/env python3
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
    val keyId: String? = null,
    val key: String? = null,
    val quality: String? = null,
) {
    val isDash: Boolean get() = url.contains(".mpd", ignoreCase = true)
    val isHls: Boolean get() =
        url.contains(".m3u8", ignoreCase = true) ||
            url.substringBefore('?').endsWith(".txt", ignoreCase = true)
}

data class Channel(
    val name: String,
    val logo: String? = null,
    val sources: List<Source>,
    val categoria: String? = null,
)

'''

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
        elif fonte is not None and campo in ("referer", "agente", "chave", "qualidade"):
            fonte[{"agente": "userAgent", "chave": "key", "qualidade": "quality"}.get(campo, campo)] = valor
    return [c for c in channels if c["sources"]]

def emit_chunk(out, missing, channels, chunk_name):
    out.append(f"private fun build_{chunk_name}(): List<Channel> = listOf(")
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
            if source.get("quality"):
                out.append(f'                quality = {quote(source["quality"])},')
            if source["userAgent"]:
                out.append(f'                userAgent = {quote(source["userAgent"])},')
            if source["key"]:
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
    out.append(")")
    out.append("")

def emit_list(out, missing, channels, list_name):
    CHUNK_SIZE = 50  # Smaller chunks to be perfectly safe per method limit
    chunks = []
    for i in range(0, len(channels), CHUNK_SIZE):
        chunk_name = f"{list_name}_PART_{i//CHUNK_SIZE}"
        emit_chunk(out, missing, channels[i:i+CHUNK_SIZE], chunk_name)
        chunks.append(chunk_name)
    
    out.append(f"val {list_name}: List<Channel> = " + " + ".join([f"build_{c}()" for c in chunks]) if chunks else f"val {list_name}: List<Channel> = emptyList()")
    out.append("")

def main():
    out, missing = [HEADER], []

    catalog = parse_txt("catalogo.txt")
    restricted = parse_txt("restritos.txt", categoria_padrao="Adulto")

    emit_list(out, missing, catalog, "CATALOG")
    out.append("/// Só entra na lista depois do código, e só sem rede: a que vale é a")
    out.append("/// baixada pelo Remote. Ver Unlock.")
    emit_list(out, missing, restricted, "RESTRICTED")

    KOTLIN.write_text("\n".join(out) + "\n", encoding="utf-8")

    total = sum(len(c["sources"]) for c in catalog)
    com_chave = sum(1 for c in catalog for s in c["sources"] if s["key"])
    print(f"canais: {len(catalog)} | fontes: {total} | com ClearKey: {com_chave}"
          f" | reservados: {len(restricted)}")
    for item in missing:
        print(f"  SEM KID (canal DASH não vai tocar no Android): {item}")

if __name__ == "__main__":
    main()
