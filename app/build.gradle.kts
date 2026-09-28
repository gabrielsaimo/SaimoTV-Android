import java.util.Properties

plugins {
    id("com.android.application")
}

/**
 * Chave de assinatura, fora do Git (ver keystore.properties e a pasta chaves).
 *
 * A atualização por cima só instala quando a assinatura é a mesma, então a
 * chave é a identidade do aplicativo: perdê-la obriga todo mundo a desinstalar
 * e instalar de novo.
 */
val chave = Properties().apply {
    val arquivo = rootProject.file("keystore.properties")
    if (arquivo.exists()) arquivo.inputStream().use { load(it) }
}

android {
    namespace = "br.com.saimo.tv"
    compileSdk {
        version = release(37) { minorApiLevel = 2 }
    }

    defaultConfig {
        applicationId = "br.com.saimo.tv"
        // Android 6: o mínimo do Media3 e das bibliotecas androidx atuais.
        // Até a 2.0.2 era o 5 (21); quem ainda tem Android 5 fica na 2.0.2.
        minSdk = 23
        targetSdk = 37
        versionCode = 20003
        versionName = "2.0.3"
    }

    signingConfigs {
        create("saimo") {
            val arquivo = chave.getProperty("storeFile")
            if (arquivo != null) {
                storeFile = file(arquivo)
                storePassword = chave.getProperty("storePassword")
                keyAlias = chave.getProperty("keyAlias")
                keyPassword = chave.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        release {
            // R8: APK menor e código mais enxuto — abre mais rápido no TV Box.
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // Sem o keystore.properties (outra máquina), cai na de depuração —
            // serve para compilar, mas o APK publicado tem de sair daqui.
            signingConfig = if (chave.getProperty("storeFile") != null) {
                signingConfigs.getByName("saimo")
            } else {
                signingConfigs.getByName("debug")
            }
        }
        debug {
            // Igual à publicada: instalar o teste por cima da versão de
            // verdade, e vice-versa, sem "assinaturas não conferem".
            if (chave.getProperty("storeFile") != null) {
                signingConfig = signingConfigs.getByName("saimo")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        // A atualização compara a versão instalada com a do release.
        buildConfig = true
    }
    // O parser do guia é Kotlin puro, então roda em teste de JVM contra os
    // feeds de verdade — sem isso a única forma de validá-lo seria no aparelho.
    testOptions { unitTests.isReturnDefaultValues = true }
}

dependencies {
    val media3 = "1.11.1"
    // HLS e DASH nativos: no Android o ExoPlayer decodifica HEVC em TS e faz
    // ClearKey sozinho, então nada de proxy nem ffmpeg como no macOS.
    implementation("androidx.media3:media3-exoplayer:$media3")
    implementation("androidx.media3:media3-exoplayer-hls:$media3")
    implementation("androidx.media3:media3-exoplayer-dash:$media3")
    implementation("androidx.media3:media3-ui:$media3")
    // A sessão de mídia é o que o Assistant (Mi Box) e a Alexa (Fire TV) usam
    // para "próximo/anterior canal" e "abrir X" por voz enquanto o app está na
    // tela — sem ela o sistema não tem para quem mandar o comando de voz.
    implementation("androidx.media3:media3-session:$media3")
    implementation("androidx.core:core-ktx:1.19.1")
    implementation("androidx.appcompat:appcompat:1.8.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    // "Continuar assistindo" na tela inicial do Google TV.
    implementation("androidx.tvprovider:tvprovider:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0")
    implementation("io.coil-kt.coil3:coil:3.6.3")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.6.3")
    // OkHttp traz DNS-over-HTTPS pronto, que é o que contorna resolvedor
    // filtrado sem eu ter de falar DNS na mão como no macOS.
    implementation("androidx.media3:media3-datasource-okhttp:$media3")
    implementation("com.squareup.okhttp3:okhttp:5.5.0")
    implementation("com.squareup.okhttp3:okhttp-dnsoverhttps:5.5.0")
    testImplementation("junit:junit:4.13.2")
}

kotlin {
    compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17) }
}
