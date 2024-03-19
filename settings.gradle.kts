rootProject.name = "Gimli"

"util" { // Utility functions for other libraries, keys are described in DEPENDENCIES.md
    "coroutines"()
    "exposed"()
    "uri"()
}

"core" { // Extensions API
    "impl"()
}

"networking" { // Ktor
    "server"()
    "client"()

    "routing" {
        "server"()
        "client"()
    }
}

"database"() // JetBrains Exposed Extensions

"social" { // Gimli Social Extension
    "database"()
    "webfinger"()
}

"ld"() // TODO

pluginManagement {
    val abi: String by settings
    val atomicfu: String by settings
    val dokka: String by settings
    val kotlin: String by settings
    val kover: String by settings
    val licenser: String by settings

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("dev.yumi.gradle.licenser") version licenser apply false
        id("org.jetbrains.dokka") version dokka apply false
        id("org.jetbrains.kotlinx.binary-compatibility-validator") version abi apply false
        id("org.jetbrains.kotlinx.kover") version kover apply false
        kotlin("jvm") version kotlin apply false
        kotlin("multiplatform") version kotlin apply false
        kotlin("plugin.serialization") version kotlin apply false
    }
}

// region Boring fancy DSL stuff
data class Module(val name: String, val path: String) {
    inline operator fun String.invoke(submodules: Module.() -> Unit = {}) = this(parentModule = this@Module, submodules)
}

inline operator fun String.invoke(parentModule: Module? = null, submodules: Module.() -> Unit = {}) {
    val fullPath = parentModule?.path.orEmpty() + ':' + this

    include(fullPath)

    val project = project(fullPath)
    val fullName = parentModule?.let { "${it.name}-" }.orEmpty() + this

    project.name = fullName
    Module(fullName, fullPath).submodules()
}
// endregion
