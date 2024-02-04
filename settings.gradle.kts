rootProject.name = "Gimli"

include(
    "api:client",
    "api:common",
    "api:server",
    "ld",
    "server:database",
    "server:impl",
    "server:networking",
    "util",
)

pluginManagement {
    val kotlin: String by settings

    repositories.mavenCentral()

    plugins {
        kotlin("multiplatform") version kotlin apply false
        kotlin("jvm") version kotlin apply false
        kotlin("plugin.serialization") version kotlin apply false
    }
}
