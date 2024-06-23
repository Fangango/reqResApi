import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("io.strikt:strikt-core:0.34.1")
    testImplementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    testImplementation("com.google.code.gson:gson:2.10.1")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    testImplementation("io.github.oshai:kotlin-logging-jvm:6.0.9")
    testImplementation("ch.qos.logback:logback-classic:1.2.6")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}