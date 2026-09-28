plugins {
    id("com.android.application") version "9.4.1" apply false
    // O AGP 9 já traz o Kotlin embutido; declarar o plugin aqui só fixa a
    // versão do compilador na mais nova.
    id("org.jetbrains.kotlin.android") version "2.4.20" apply false
}
