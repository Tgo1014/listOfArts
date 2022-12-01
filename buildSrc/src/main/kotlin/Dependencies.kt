import Dependencies.Versions.accompanist
import Dependencies.Versions.compose
import Dependencies.Versions.composeCompiler

object Dependencies {

    object Versions {
        val kotlin = "1.7.21"
        val hilt = "2.44.2"
        val compose = "1.4.0-alpha02"
        val composeCompiler = "1.4.0-alpha02"
        val lifecycle = "2.6.0-alpha03"
        val accompanist = "0.28.0"
        val androidPlugins = "7.3.1"
        val detekt = "1.22.0"
        val spotless = "6.12.0"
    }

    object BuildPlugins {
        val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    }

    object Android {
        val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
    }

    object Detekt {
        val twitter = "com.twitter.compose.rules:detekt:0.0.26"
    }

    object Network {
        val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
        val kotlinxSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
        val interceptor = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.10"
    }

    object Compose {
        val ui = "androidx.compose.ui:ui:$compose"
        val material = "androidx.compose.material:material:$compose"
        val material3 = "androidx.compose.material3:material3:1.1.0-alpha02"
        val toolPreview = "androidx.compose.ui:ui-tooling-preview:$compose"
        val uiUtil = "androidx.compose.ui:ui-util:$compose"
        val compiler = "androidx.compose.compiler:compiler:$composeCompiler"
        val activityCompose = "androidx.activity:activity-compose:1.7.0-alpha02"
        val coil = "io.coil-kt:coil-compose:2.2.2"

        object Accompanist {
            val pager = "com.google.accompanist:accompanist-pager:$accompanist"
            val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:$accompanist"
            val navigation = "com.google.accompanist:accompanist-navigation-material:$accompanist"
        }

    }

    object Injection {
        val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        val hiltKapt = "com.google.dagger:hilt-compiler:${Versions.hilt}"
        val hiltCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

    object Test {
        val jUnit = "junit:junit:4.13.2"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
        val turbine = "app.cash.turbine:turbine:0.12.1"
        val mockWebServer = "com.squareup.okhttp3:mockwebserver:5.0.0-alpha.10"
    }

    val timber = "com.jakewharton.timber:timber:5.0.1"
}