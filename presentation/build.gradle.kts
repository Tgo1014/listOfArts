@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("io.gitlab.arturbosch.detekt") version Dependencies.Versions.detekt
    kotlin("kapt")
}

android {
    namespace = "tgo1014.listofbeers.presentation"
    compileSdk = Dependencies.compileSdk
    defaultConfig.minSdk = Dependencies.minSdk
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = Dependencies.Versions.composeCompiler
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
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
    testImplementation(Dependencies.Test.coroutinesTest)
    testImplementation(Dependencies.Test.turbine)
    detektPlugins(Dependencies.Detekt.twitter)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += Dependencies.optIns
}