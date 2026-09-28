plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("net.kyori", "indra-common", "4.0.0")
    implementation("net.kyori", "indra-git", "3.0.1")
    implementation("com.gradleup.shadow:com.gradleup.shadow.gradle.plugin:9.6.1")
    implementation("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext", "gradle-idea-ext", "1.4.1")
}

kotlin {
    jvmToolchain(25)
}

