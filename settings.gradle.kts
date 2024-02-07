rootProject.name = "Gimli"

include(
    "util",
    "api:common",
    "api:client",
    "api:server",
    "ld",
    "server:database",
    "server:impl",
    "server:networking",
)

pluginManagement {
    val kotlin: String by settings
    val licenser: String by settings

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        kotlin("multiplatform") version kotlin apply false
        kotlin("jvm") version kotlin apply false
        kotlin("plugin.serialization") version kotlin apply false
        id("dev.yumi.gradle.licenser") version licenser apply false
    }
}
