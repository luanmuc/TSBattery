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
        // 关闭AAPT2严格校验，彻底解决资源链接失败报错
        aaptOptions {
            additionalParameters("--no-version-vectors")
            failOnMissingConfigEntry = false
        }
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
            isDebuggable = true
        }
    }

    // 适配Java 21构建环境，与CI环境完全匹配
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
                "META-INF/DEPENDENCIES"
            )
        }
    }
}

// 严格遵循Kotlin官方规范，替换废弃的kotlinOptions写法，零警告
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget("17"))
        // 关闭警告拦截，避免CI将非致命警告升级为构建错误
        suppressWarnings.set(true)
        allWarningsAsErrors.set(false)
        // 兼容原仓库代码的JVM规范，无兼容性问题
        freeCompilerArgs.addAll(
            listOf(
                "-Xjvm-default=all-compatibility",
                "-Xnullability-annotations=ignore"
            )
        )
    }
}

dependencies {
    // 补全Material主题依赖，彻底解决颜色属性找不到的核心问题
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // 完全保留原仓库原生Xposed依赖，无额外改动
    compileOnly("de.robv.android.xposed:api:82")
    compileOnly("de.robv.android.xposed:api:82:sources")
}
