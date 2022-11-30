plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version Dependencies.Versions.kotlin
    kotlin("kapt")
}

android {
    namespace = "tgo1014.listofbeers.data"
    compileSdk = 33
//
//    defaultConfig {
//        minSdk 21
//        targetSdk 33
//
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles "consumer-rules.pro"
//    }
//
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//    compileOptions {
//        sourceCompatibility JavaVersion.VERSION_1_8
//        targetCompatibility JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = '1.8'
//    }
}

dependencies {
    implementation(project(":domain"))
    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.serialization)
    implementation(Dependencies.Injection.hilt)
    kapt(Dependencies.Injection.hiltKapt)
}