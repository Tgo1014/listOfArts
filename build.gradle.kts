buildscript {
    dependencies {
        classpath(Dependencies.BuildPlugins.hilt)
    }
}
plugins {
    kotlin("jvm") version Dependencies.Versions.kotlin apply false
    kotlin("android") version Dependencies.Versions.kotlin apply false
    id("com.android.application") version Dependencies.Versions.androidPlugins apply false
    id("com.android.library") version Dependencies.Versions.androidPlugins apply false
    id("com.diffplug.spotless") version Dependencies.Versions.spotless apply false
}
subprojects {
    apply(plugin = "com.diffplug.spotless")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            ktlint()
                .setUseExperimental(true)
                .editorConfigOverride(
                    mapOf(
                        "ij_kotlin_allow_trailing_comma" to "true",
                        "code_style" to "android",
                        "android" to "true"
                    )
                )
        }
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}