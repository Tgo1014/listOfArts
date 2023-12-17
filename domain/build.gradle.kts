plugins {
    `java-library`
    kotlin("jvm") version Dependencies.Versions.kotlin
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(Dependencies.coroutines)
    implementation(Dependencies.Injection.javax)
    testImplementation(kotlin("test"))
    testImplementation(Dependencies.Test.coroutinesTest)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += Dependencies.optIns
}