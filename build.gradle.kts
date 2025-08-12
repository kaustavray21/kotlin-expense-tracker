plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.compose") version "1.6.11"
    kotlin("plugin.compose") version "2.0.0"
    id("app.cash.sqldelight") version "2.0.2"
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.2")
    implementation("com.github.tehras:charts:0.2.4-alpha")
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

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.expensetracker.db")
        }
    }
}
