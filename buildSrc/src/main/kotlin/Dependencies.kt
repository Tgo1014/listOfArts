object Dependencies {

    object Versions {
        val kotlin = "1.7.21"
        val hilt = "2.44.2"
    }

    object BuildPlugins {
        val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    }

    object Network {
        val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
    }

    object Injection {
        val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        val hiltKapt = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

}