// 适配Gradle 9.2的稳定版AGP + Kotlin版本，全量兼容原仓库代码
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
