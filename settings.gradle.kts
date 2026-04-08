pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        // 必须加JCenter，Xposed API 82仅在此仓库可用
        jcenter()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // 必须加JCenter，Xposed API 82仅在此仓库可用
        jcenter()
    }
}

rootProject.name = "TSBattery"
include(":app")
