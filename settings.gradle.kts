pluginManagement {
    repositories {
        maven(url = "https://maven.fabricmc.net/")
        maven(url = "https://maven.architectury.dev/")
        maven(url = "https://maven.minecraftforge.net/")
        gradlePluginPortal()
    }
}

//include("common")
include("1.20.1:")
include("1.20.1:common:", "1.20.1:fabric:", "1.20.1:forge:")
include("1.20.2:")
include("1.20.2:common:", "1.20.2:fabric:", "1.20.2:forge:")
rootProject.name = "MythLib"
