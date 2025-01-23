@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "tgo1014.listofarts.presentation"
    compileSdk = libs.versions.sdk.compile.get().toInt()
    defaultConfig.minSdk = libs.versions.sdk.min.get().toInt()
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    compileOptions {
        sourceCompatibility(libs.versions.jvm.get().toInt())
        targetCompatibility(libs.versions.jvm.get().toInt())
    }
    kotlinOptions.jvmTarget = libs.versions.jvm.get().toString()
}

kotlin {
    compilerOptions.freeCompilerArgs.add(libs.versions.optIns.get())
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.util)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.coil.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.navigation.material)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.zoomimage.compose.coil)
    implementation(libs.androidx.material.icons.extended)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockito.kotlin)
    detektPlugins(libs.detekt)
}