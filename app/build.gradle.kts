plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.fankes.tsbattery"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fankes.tsbattery"
        minSdk = 24
        targetSdk = 34
        versionCode = 20260408
        versionName = "2026.04.08_FixBuild"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // 保留原项目依赖不变，只加核心Xposed依赖
    compileOnly("de.robv.android.xposed:api:82")
    compileOnly("de.robv.android.xposed:api:82:sources")
}
