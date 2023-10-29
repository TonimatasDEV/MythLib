@file:Suppress("DEPRECATION", "UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.task.RemapJarTask
import java.io.File
import java.util.Properties

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

architectury {
    platformSetupLoomIde()
    forge()
}
val minecraftVersion = "1.20.2"
val properties = File("$minecraftVersion/gradle.properties").takeIf { it.exists() }?.inputStream()?.use { Properties().apply { load(it) } }
val forgeVersion = properties?.getProperty("forgeVersion")
val forgeLoaderRange = properties?.getProperty("forgeLoaderRange")
val modVersion = properties?.getProperty("modVersion")

val common by configurations.creating
val shadowCommon by configurations.creating

configurations["compileClasspath"].extendsFrom(common)
configurations["runtimeClasspath"].extendsFrom(common)
configurations["developmentForge"].extendsFrom(common)

dependencies {
    forge("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")

    common(project(path = ":$minecraftVersion:common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":$minecraftVersion:common", configuration = "transformProductionForge")) { isTransitive = false }
}

tasks.withType<ProcessResources> {
    val replaceProperties = mapOf("modVersion" to modVersion, "forgeLoaderRange" to forgeLoaderRange, "forgeMinecraftVersionRange" to "[$minecraftVersion]")
    inputs.properties(replaceProperties)

    filesMatching("META-INF/mods.toml") {
        expand(replaceProperties)
    }
}

tasks.withType<ShadowJar> {
    exclude("fabric.mod.json")

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
