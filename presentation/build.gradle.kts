@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "tgo1014.listofbeers.presentation"
    compileSdk = Dependencies.compileSdk
    defaultConfig.minSdk = Dependencies.minSdk
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = Dependencies.Versions.composeCompiler
}

dependencies {
    implementation(project(":domain"))
    implementation(Dependencies.Android.lifecycle)
    implementation(Dependencies.Android.lifecycleRuntime)
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.compiler)
    implementation(Dependencies.Compose.uiUtil)
    debugImplementation(Dependencies.Compose.toolPreview)
    implementation(Dependencies.Compose.preview)
    implementation(Dependencies.Compose.coil)
    implementation(Dependencies.Compose.activityCompose)
    implementation(Dependencies.Compose.Accompanist.navigation)
    implementation(Dependencies.Compose.Accompanist.systemUiController)
    implementation(Dependencies.Injection.hilt)
    implementation(Dependencies.Injection.hiltCompose)
    kapt(Dependencies.Injection.hiltKapt)
    testImplementation(Dependencies.Test.jUnit)
    testImplementation(Dependencies.Test.coroutines)
    testImplementation(Dependencies.Test.turbine)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
}