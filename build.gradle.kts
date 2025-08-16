group = "me.jakejmattson"
version = "1.0.0"

plugins {
    kotlin("jvm") version "2.2.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.7.0")
    implementation("org.slf4j:slf4j-nop:2.0.7")
}

tasks {
    javaToolchains {
        compilerFor {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    shadowJar {
        archiveFileName.set("crop.jar")
        manifest {
            attributes("Main-Class" to "me.jakejmattson.crop.MainKt")
        }
    }
}