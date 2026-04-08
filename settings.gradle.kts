pluginManagement {
    repositories {
        // 阿里云镜像优先，彻底解决国外仓库关停、访问失败问题
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        // 官方备用仓库
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 阿里云镜像优先，100%能拉取到Xposed API 82依赖
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        // 官方备用仓库
        google()
        mavenCentral()
    }
}

rootProject.name = "TSBattery"
include(":app")
