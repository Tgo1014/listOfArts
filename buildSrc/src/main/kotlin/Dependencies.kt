import Dependencies.Versions.accompanist
import Dependencies.Versions.compose
import Dependencies.Versions.composeCompiler
import Dependencies.Versions.coroutines

object Dependencies {

    val compileSdk = 34
    val minSdk = 21

    object Versions {
        val kotlin = "1.9.21"
        val coroutines = "1.7.3"
        val hilt = "2.49"
        val compose = "1.6.0-beta03"
        val composeCompiler = "1.5.6"
        val lifecycle = "2.6.0-alpha03"
        val accompanist = "0.33.2-alpha"
        val androidPlugins = "8.2.0"
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
        val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2"
        val kotlinxSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0"
        val interceptor = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11"
    }

    object Compose {
        val ui = "androidx.compose.ui:ui:$compose"
        val material = "androidx.compose.material:material:$compose"
        val material3 = "androidx.compose.material3:material3:1.2.0-alpha12"
        val toolPreview = "androidx.compose.ui:ui-tooling:$compose"
        val preview = "androidx.compose.ui:ui-tooling-preview:1.6.0-beta02"
        val uiUtil = "androidx.compose.ui:ui-util:$compose"
        val compiler = "androidx.compose.compiler:compiler:$composeCompiler"
        val activityCompose = "androidx.activity:activity-compose:1.9.0-alpha01"
        val coil = "io.coil-kt:coil-compose:2.5.0"

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
        val javax = "javax.inject:javax.inject:1"
    }

    object Test {
        val jUnit = "junit:junit:4.13.2"
        val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        val turbine = "app.cash.turbine:turbine:0.12.1"
        val mockWebServer = "com.squareup.okhttp3:mockwebserver:5.0.0-alpha.10"
        val testRunner = "androidx.test:runner:1.5.1"
        val jUnitExtensions = "androidx.test.ext:junit-ktx:1.1.4"
        val espresso = "androidx.test.espresso:espresso-core:3.5.0"
        val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
        val hiltTestingKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        val composeJunit = "androidx.compose.ui:ui-test-junit4:$compose"
        val composeTestManifest = "androidx.compose.ui:ui-test-manifest:$compose"
    }

    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val timber = "com.jakewharton.timber:timber:5.0.1"
    val optIns = "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi,com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi"
}