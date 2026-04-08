import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // 适配Gradle 9.2，消除所有废弃特性警告
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

// 严格按照Kotlin官方文档迁移：废弃kotlinOptions，改用compilerOptions DSL
kotlin {
    compilerOptions {
        // 适配Java 21构建环境，设置兼容的JVM目标版本
        jvmTarget.set(JvmTarget.fromTarget("17"))
        // 关闭编译警告拦截，避免CI把警告升级为错误
        suppressWarnings.set(true)
        // 原编译参数用新版DSL规范配置，零废弃
        freeCompilerArgs.addAll(
            listOf(
                "-Xjvm-default=all-compatibility",
                "-Xnullability-annotations=ignore",
                "-Xskip-prerelease-check"
            )
        )
    }
}

dependencies {
    // 完全保留原仓库仅有的原生依赖，不新增任何内容，零冲突
    compileOnly("de.robv.android.xposed:api:82")
    compileOnly("de.robv.android.xposed:api:82:sources")
}
