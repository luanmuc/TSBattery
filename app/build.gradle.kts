plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    // 🔥 必须设置为 36 (Android 16) 以支持 LSPosed 2.0 (API 101)
    compileSdk = 36
    namespace = "com.fankes.tsbattery"

    defaultConfig {
        applicationId = "com.fankes.tsbattery"
        minSdk = 24
        // 🔥 目标 SDK 必须 36，否则新微信会屏蔽模块
        targetSdk = 36
        versionCode = 20260408
        versionName = "2026.04.08_Final_All_Optimized"

        // 启用 ViewBinding (如果原项目没有启用，建议加上，防止报错)
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        // 🔥 必须使用 Java 17 (Android 14+ 要求)
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        // 🔥 Kotlin 编译目标必须是 17
        jvmTarget = "17"
    }

    // 🔥 开启对 Xposed/LSPosed API 101 的兼容支持
    packaging {
        resources.excludes.add("**/attach.base.so")
        resources.excludes.add("**/libart.so")
        resources.excludes.add("**/libandroid_runtime.so")
    }
}

dependencies {
    // 🔥 核心依赖：YukiHookAPI 1.2.7 (完美适配 API 101 / LSPosed 2.0)
    implementation("com.highcapable.yukihookapi:api:1.2.7")
    
    // 🔥 Xposed API 依赖 (必须有，用于编译通过)
    compileOnly("de.robv.android.xposed:api:82")
    compileOnly("de.robv.android.xposed:api:82:sources")
}
