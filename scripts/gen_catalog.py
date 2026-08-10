import json, unicodedata, re

SRC = "/Volumes/SSD 1TB/DEV/Saimo/SaimoPlayer/Sources/Channels.swift"

def norm(s):
    s = unicodedata.normalize('NFKD', s).encode('ascii', 'ignore').decode().lower()
    return re.sub(r'[^a-z0-9]', '', s)

def sw(s):
    return '"' + s.replace('\\', '\\\\').replace('"', '\\"') + '"'

def entry(name, url, logo=None, referer=None, ua=None, key=None):
    parts = [f'name: {sw(name)}', f'source: {sw(url)}']
    parts.append(f'logo: {sw(logo)}' if logo else 'logo: nil')
    parts.append(f'referer: {sw(referer)}' if referer else 'referer: nil')
    parts.append(f'userAgent: {sw(ua)}' if ua else 'userAgent: nil')
    parts.append(f'clearKey: {sw(key)}' if key else 'clearKey: nil')
    return "    Source(" + ",\n           ".join(parts) + "),"

# Preserve the hand-maintained HLS block already in Channels.swift.
existing = open(SRC, encoding="utf-8").read()
block = existing[existing.index("private let catalog"):existing.index("]\n\nlet defaultChannels")]
kept = []
for m in re.finditer(r'Source\(name: "((?:[^"\\]|\\.)*)",\s*\n\s*source: "((?:[^"\\]|\\.)*)",\s*\n\s*logo: (nil|"(?:[^"\\]|\\.)*"),\s*\n\s*referer: (nil|"(?:[^"\\]|\\.)*")', block):
    name, url, logo, ref = m.group(1), m.group(2), m.group(3), m.group(4)
    kept.append((name, url,
                 None if logo == "nil" else logo.strip('"'),
                 None if ref == "nil" else ref.strip('"')))

data = json.load(open('offline.json'))['data']
logos = {norm(c['name']): c.get('logo', '') for c in data if c.get('logo')}
dash = json.load(open('working_dash.json'))

lines = ["import Foundation\n", """/// Static channel line-up.
///
/// `source` is the upstream playlist. HLS with HEVC-in-TS and every DASH source
/// are repackaged by the ffmpeg gateway before AVFoundation sees them; DASH
/// entries additionally carry the CENC ClearKey.
struct Source {
    let name: String
    let source: String
    let logo: String?
    let referer: String?
    let userAgent: String?
    let clearKey: String?
}
""", "private let catalog: [Source] = [", "    // HLS"]

known_urls = {u for _, u, _, _ in kept}
for name, url, logo, ref in kept:
    lines.append(entry(name, url, logo, ref))

lines += ["", "    // DASH + ClearKey, validados por decodificação limpa"]
added = 0
for c in sorted(dash, key=lambda x: x['name']):
    if c['url'] in known_urls:
        continue
    lines.append(entry(c['name'], c['url'],
                       c.get('logo') or logos.get(norm(c['name'])),
                       c.get('ref') or None, c.get('ua') or None, c.get('key') or None))
    added += 1

lines += ["]\n", """let defaultChannels: [Channel] = catalog.compactMap { s in
    guard let url = URL(string: s.source) else { return nil }
    return Channel(name: s.name,
                   source: url,
                   logo: s.logo.flatMap(URL.init(string:)),
                   referer: s.referer,
                   userAgent: s.userAgent,
                   clearKey: s.clearKey)
}
"""]

open(SRC, "w", encoding="utf-8").write("\n".join(lines))
print(f"HLS mantidos: {len(kept)} | DASH adicionados: {added} | total: {len(kept)+added}")
