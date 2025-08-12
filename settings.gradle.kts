plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

// This block configures the repositories for the entire project.
// It is the single source of truth for where Gradle should look for dependencies.
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        // This repository is required for the compose-chart library
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        // This repository was in your original build file and is now included here
        maven("https://jitpack.io")
    }
}

rootProject.name = "ExpenseTracker"
