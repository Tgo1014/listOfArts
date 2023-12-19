import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.hilt).apply(false)
    alias(libs.plugins.detekt).apply(false)
    alias(libs.plugins.spotless).apply(false)
}

subprojects {
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)
    configure<SpotlessExtension> {
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