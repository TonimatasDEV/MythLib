import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    java
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.3-SNAPSHOT" apply false
    `maven-publish`
}

val minecraftVersion: String = project.name
val modVersion: String by extra

architectury {
    minecraft = minecraftVersion
}

subprojects {
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "maven-publish")

    base {
        archivesName.set("MythLib-" + project.name)
    }

    configure<LoomGradleExtensionAPI> {
        silentMojangMappingsLicense()
    }

    dependencies {
        "minecraft"("com.mojang:minecraft:$minecraftVersion")
        "mappings"(project.the<LoomGradleExtensionAPI>().officialMojangMappings())
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifactId = "mythlib-${project.name}-$modVersion"
                from(components["java"])

                pom {
                    name.set("MythLib ${project.name}")
                    url.set("https://github.com/TonimatasDEV/MythLib")

                    scm {
                        connection.set("git:https://github.com/TonimatasDEV/MythLib.git")
                        developerConnection.set("git:https://github.com/TonimatasDEV/MythLib.git")
                        url.set("https://github.com/TonimatasDEV/MythLib")
                    }

                    licenses {
                        license {
                            name.set("MIT")
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                setUrl("http://209.192.217.228:25580/")
                credentials {
                    username = System.getenv("USERNAME")
                    password = System.getenv("PASSWORD")
                }
            }
        }
    }
}

allprojects {
    apply(plugin = "architectury-plugin")
}
