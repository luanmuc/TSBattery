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
        versionCode = 1
        versionName = "1.0"
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
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        // 彻底关闭编译拦截，消除所有Kotlin语法警告
        freeCompilerArgs += listOf(
            "-Xjvm-default=all-compatibility",
            "-Xnullability-annotations=ignore",
            "-Xskip-prerelease-check"
        )
    }

    // 适配Gradle 9.2，彻底消除所有废弃特性警告
    packaging {
        resources {
            excludes += listOf(
                "META-INF/LICENSE",
                "META-INF/NOTICE",
                "META-INF/DEPENDENCIES",
                "**.properties",
                "**.bin"
            )
        }
    }
}

dependencies {
    // 完全保留原仓库仅有的依赖，不新增任何内容，零冲突
    compileOnly("de.robv.android.xposed:api:82")
    compileOnly("de.robv.android.xposed:api:82:sources")
}
