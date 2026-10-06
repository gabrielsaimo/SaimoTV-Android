with open("app/src/main/java/br/com/saimo/tv/EventosActivity.kt", "r") as f:
    text = f.read()

text = text.replace(
"""            }
        }
        }
        
        override fun getItemCount() = itens.size
    }""",
"""            }
        }
        
        override fun getItemCount() = itens.size
    }"""
)

with open("app/src/main/java/br/com/saimo/tv/EventosActivity.kt", "w") as f:
    f.write(text)

with open("app/src/main/res/layout/item_evento.xml", "r") as f:
    xml = f.read()

xml = xml.replace("@drawable/fundo_card", "#222222")

with open("app/src/main/res/layout/item_evento.xml", "w") as f:
    f.write(xml)
