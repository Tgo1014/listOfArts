buildscript {
    dependencies {
        classpath(Dependencies.BuildPlugins.hilt)
    }
}
plugins {
    id("com.android.application")       version Dependencies.Versions.androidPlugins    apply false
    id("com.android.library")           version Dependencies.Versions.androidPlugins    apply false
    id("org.jetbrains.kotlin.jvm")      version Dependencies.Versions.kotlin            apply false
    id("org.jetbrains.kotlin.android")  version Dependencies.Versions.kotlin            apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}