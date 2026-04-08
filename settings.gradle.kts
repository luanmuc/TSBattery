pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 官方依赖仓库，确保能拉取所有Android官方依赖
        google()
        mavenCentral()
    }
}

rootProject.name = "TSBattery"
include(":app")
