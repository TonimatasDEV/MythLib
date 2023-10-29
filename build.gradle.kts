plugins {
    java
}

val modVersion: String by extra

subprojects {
    apply(plugin = "java")

    version = modVersion
    group = "dev.tonimatas.mythlib"

    repositories {

    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    java {
        withSourcesJar()
    }
}