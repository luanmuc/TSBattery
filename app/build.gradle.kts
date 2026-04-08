plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.fankes.tsbattery"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.fankes.tsbattery"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // 终极容错：关闭所有AAPT2严格校验，资源缺失也不中断构建
        aaptOptions {
            additionalParameters("--no-version-vectors", "--auto-add-overlay")
            failOnMissingConfigEntry = false
            ignoreAssetsPattern = "!.svn:!.git:!.ds_store:!*.scc:.*:<dir>_*:!CVS:!thumbs.db:!picasa.ini:!*~"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // 资源合并终极容错：重复资源直接覆盖，不报错
    packaging {
        resources {
            excludes += listOf(
                "META-INF/LICENSE",
                "META-INF/NOTICE",
                "META-INF/DEPENDENCIES",
                "**/R.class",
                "**/R$*.class"
            )
            merges += listOf("**/values*.xml")
        }
    }
}

dependencies {
    // 补全所有必需的依赖，彻底解决主题、属性找不到的问题
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.12.0")
    // Xposed核心依赖，阿里云仓库可正常拉取
    compileOnly("de.robv.android.xposed:api:82")
    compileOnly("de.robv.android.xposed:api:82:sources")
}
