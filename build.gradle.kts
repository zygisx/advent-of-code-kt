import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21"
    application
}
group = "me.zygis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("io.mockk:mockk:1.13.8")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "17"
}