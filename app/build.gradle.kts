plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.saimo.tv"
    compileSdk = 35

    defaultConfig {
        applicationId = "br.com.saimo.tv"
        // Alcança os TV Box antigos ainda em uso.
        minSdk = 21
        targetSdk = 35
        versionCode = 10400
        versionName = "1.4"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
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
    val media3 = "1.4.1"
    // HLS e DASH nativos: no Android o ExoPlayer decodifica HEVC em TS e faz
    // ClearKey sozinho, então nada de proxy nem ffmpeg como no macOS.
    implementation("androidx.media3:media3-exoplayer:$media3")
    implementation("androidx.media3:media3-exoplayer-hls:$media3")
    implementation("androidx.media3:media3-exoplayer-dash:$media3")
    implementation("androidx.media3:media3-ui:$media3")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("io.coil-kt:coil:2.7.0")
    // OkHttp traz DNS-over-HTTPS pronto, que é o que contorna resolvedor
    // filtrado sem eu ter de falar DNS na mão como no macOS.
    implementation("androidx.media3:media3-datasource-okhttp:$media3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:okhttp-dnsoverhttps:4.12.0")
    testImplementation("junit:junit:4.13.2")
}
