plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "tgo1014.listofbeers.data"
    compileSdk = libs.versions.sdk.compile.get().toInt()
    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
        testOptions.targetSdk = libs.versions.sdk.target.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    debugImplementation(libs.ui.test.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.mockwebserver)
    testImplementation(libs.retrofit2.kotlinx.serialization.converter)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += libs.versions.optIns.get()
}