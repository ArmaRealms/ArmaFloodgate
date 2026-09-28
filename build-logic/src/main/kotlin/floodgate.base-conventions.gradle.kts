plugins {
    `java-library`
//    id("net.ltgt.errorprone")
    id("net.kyori.indra")
    id("net.kyori.indra.git")
}

dependencies {
    compileOnly("org.checkerframework", "checker-qual", Versions.checkerQual)
}

indra {
    github("GeyserMC", "Floodgate") {
        ci(true)
        issues(true)
        scm(true)
    }
    mitLicense()

    javaVersions {
        // Compile each Java module with the Java 25 toolchain and bytecode target.
        minimumToolchain(25)
        strictVersions(true)
        target(25)
    }
}

tasks {
    processResources {
        filesMatching(listOf("plugin.yml", "bungee.yml", "velocity-plugin.json")) {
            expand(
                "id" to "floodgate",
                "name" to "floodgate",
                "version" to fullVersion(),
                "description" to project.description.orEmpty(),
                "url" to "https://geysermc.org",
                "author" to "GeyserMC"
            )
        }
    }
}