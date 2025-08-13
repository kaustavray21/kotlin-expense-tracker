plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.compose") version "1.6.11"
    kotlin("plugin.compose") version "2.0.0"
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

compose.desktop {
    application {
        mainClass = "com.expensetracker.MainKt"
    }
}