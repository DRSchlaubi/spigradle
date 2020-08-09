import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

/**
 * Location of developers plugins directory in gradle.properties.
 */
val spigotPluginsDir: String? by project

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    // mavenLocal() // if you need NMS
}

dependencies {
    compileOnly("org.spigotmc", "spigot-api", "1.16.1-R0.1-SNAPSHOT")
}

tasks {
    // If you open resources/plugins.yml you will see "@version@" as the version this code replaces this
    processResources {
        from(sourceSets["main"].resources) {
            val tokens = mapOf("version" to version)
            filter(ReplaceTokens::class, mapOf("tokens" to tokens))
        }
    }


    // This allows you to install your plugin using gradle installPlugin
    task<Copy>("installPlugin") {
        dependsOn(jar)
        from(jar)
        include("*.jar")
        into(spigotPluginsDir ?: error("Please set spigotPluginsDir in gradle.properties"))
    }
}
