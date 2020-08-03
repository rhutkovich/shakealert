/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

plugins {
    id("java")
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.72"

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

sourceSets.main {
    java.srcDirs("src/com.hutkovich.shakealert.main/java", "src/com.hutkovich.shakealert.main/kotlin")
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("joda-time:joda-time:2.10.6")
    implementation("org.telegram:telegrambots:4.9.1")
    implementation("net.sourceforge.htmlunit:com.springsource.com.gargoylesoftware.htmlunit:2.6.0")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    // Define the com.hutkovich.shakealert.main class for the application.
    mainClassName = "com.hutkovich.shakealert.AppKt"
}