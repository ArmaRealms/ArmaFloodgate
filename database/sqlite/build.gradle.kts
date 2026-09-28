val sqliteJdbcVersion = "3.36.0.3"

dependencies {
    provided(projects.core)
    implementation("org.xerial", "sqlite-jdbc", sqliteJdbcVersion)

    testImplementation(projects.core)
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.4")
}

description = "The Floodgate database extension for SQLite"

tasks.test {
    useJUnitPlatform()
}
