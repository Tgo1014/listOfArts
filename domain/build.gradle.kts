plugins {
    `java-library`
    kotlin("jvm") version Dependencies.Versions.kotlin
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}