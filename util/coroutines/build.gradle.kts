import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
    gimli
}

val coroutines: String by properties
val jdk: String by properties


kotlin {
    jvm()

    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        binaries.library()
    }

    sourceSets.commonMain.dependencies {
        api(gimliModule(":util"))
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    }

    sourceSets.commonTest.dependencies {
        api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines")
    }
}
