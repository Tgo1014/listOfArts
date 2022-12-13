plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version Dependencies.Versions.kotlin
    kotlin("kapt")
}

android {
    namespace = "tgo1014.listofbeers.data"
    compileSdk = 33
    defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

dependencies {
    implementation(project(":domain"))
    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.serialization)
    implementation(Dependencies.Injection.hilt)
    kapt(Dependencies.Injection.hiltKapt)
    testImplementation(Dependencies.Test.jUnit)
    testImplementation(Dependencies.Test.coroutinesTest)
    testImplementation(Dependencies.Test.mockWebServer)
    testImplementation(Dependencies.Network.kotlinxSerializationConverter)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += Dependencies.optIns
}