import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
}

group = "pablo_lonav"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven {
        url = uri("https://repo.clojars.org")
        name = "Clojars"
    }
    maven {
        url = uri("https://repo.clojars.org")
        name = "Clojars"
    }
    maven {
        url = uri("https://repo.clojars.org")
        name = "Clojars"
    }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1-native-mt")

    implementation("com.google.code.gson:gson:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")

    implementation("io.ktor:ktor-client-core:2.0.1")
    implementation("io.ktor:ktor-client-cio:2.0.1")
    implementation("io.ktor:ktor-client-json:2.0.1")
    implementation("io.ktor:ktor-client-logging:2.0.1")
    implementation("io.ktor:ktor-client-serialization:2.0.1")

    implementation("co.touchlab:kermit:1.1.1")

    implementation("io.insert-koin:koin-core:3.1.6")
    implementation("keechma:router:1.0.3")
    implementation("se.codeunlimited.android.bitmap_utils:android-bitmap-utils:0.4")
    testImplementation("io.insert-koin:koin-test:3.1.6")

    //COIL
    //implementation("io.coil-kt:coil-compose:2.0.0-rc02")

    //implementation("com.github.skydoves:landscapist-coil:1.5.1")

    //PAGER
    //implementation("com.google.accompanist:accompanist-pager:0.23.1")

}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "IlustraShopDesktop"
            packageVersion = "1.0.0"
        }
    }
}