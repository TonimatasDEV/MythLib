@file:Suppress("DEPRECATION")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.task.RemapJarTask
import org.gradle.api.component.AdhocComponentWithVariants
import java.io.File
import java.util.Properties

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val minecraftVersion = "1.20.2"
val properties = File("$minecraftVersion/gradle.properties").takeIf { it.exists() }?.inputStream()?.use { Properties().apply { load(it) } }
val fabricApiVersion = properties?.getProperty("fabricApiVersion")
val fabricLoaderVersion: String by extra
val modVersion = properties?.getProperty("modVersion")

val common by configurations.creating
val shadowCommon by configurations.creating

configurations["compileClasspath"].extendsFrom(common)
configurations["runtimeClasspath"].extendsFrom(common)
configurations["developmentFabric"].extendsFrom(common)

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")

    // Dependencies (OPTIONAL)
    modApi("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion+$minecraftVersion") // Fabric API

    include(modApi("teamreborn:energy:3.0.0") {
        exclude(group = "net.fabricmc", module = "fabric-api")
    })

    common(project(path = ":$minecraftVersion:common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":$minecraftVersion:common", configuration = "transformProductionFabric")) { isTransitive = false }
}

tasks.withType<ProcessResources> {
    val replaceProperties = mapOf("modVersion" to modVersion, "fabricMinecraftVersionRange" to ">=$minecraftVersion")

    inputs.properties(replaceProperties)

    filesMatching("fabric.mod.json") {
        expand(replaceProperties)
    }
}

tasks.withType<ShadowJar> {
    configurations = listOf(shadowCommon)
    archiveClassifier.set("dev-shadow")
}

tasks.withType<RemapJarTask> {
    val shadowTask = tasks.shadowJar.get()
    input.set(shadowTask.archiveFile)
    dependsOn(shadowTask)
    archiveClassifier.set("")
}

tasks.jar {
    archiveClassifier.set("dev")
}

tasks.sourcesJar {
    val commonSources = project(":$minecraftVersion:common").tasks.sourcesJar.get()
    dependsOn(commonSources)
    from(commonSources.archiveFile.map { zipTree(it) })
}

components.getByName<AdhocComponentWithVariants>("java").apply {
    withVariantsFromConfiguration(project.configurations["shadowRuntimeElements"]) {
        skip()
    }
}
