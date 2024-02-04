import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

val collections: String by properties
val coroutines: String by properties
val datetime: String by properties
val jdk: String by properties
val klogging: String by properties
val logback: String by properties
val serialization: String by properties

kotlin {
    applyDefaultHierarchyTemplate()

    @OptIn(ExperimentalKotlinGradlePluginApi::class) compilerOptions {
        allWarningsAsErrors = true
        explicitApi = ExplicitApiMode.Strict
        verbose = true
    }

    jvm {
        jvmToolchain(jdk.toInt())
    }

    @OptIn(ExperimentalWasmDsl::class) wasmJs().browser()

    sourceSets.commonMain.dependencies {
        api("io.github.oshai:kotlin-logging:$klogging")
        api("org.jetbrains.kotlinx:kotlinx-collections-immutable:$collections")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
        api("org.jetbrains.kotlinx:kotlinx-datetime:$datetime")
        api("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
    }

    sourceSets.commonTest.dependencies {
        api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines")
    }

    sourceSets.jvmMain.dependencies {
        runtimeOnly("ch.qos.logback:logback-classic:$logback")
    }
}
