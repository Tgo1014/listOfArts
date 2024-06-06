import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "tgo1014.listofarts.domain"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig.minSdk = libs.versions.android.minSdk.get().toInt()
}

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.javaTargetVersion.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.javaTargetVersion.get())
}

@OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)
kotlin {

    compilerOptions.languageVersion.set(KotlinVersion.KOTLIN_2_0)
    jvmToolchain(libs.versions.javaTargetVersion.get().toInt())

    androidTarget()
    jvm()
    wasmJs().browser()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
        }
        jvmMain.dependencies {}
        androidMain.dependencies {}
    }
}