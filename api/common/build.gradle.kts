import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

val jdk: String by properties

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

    @OptIn(ExperimentalWasmDsl::class) wasmJs()

    sourceSets.commonMain.dependencies {
        api(project(path = ":util"))
    }
}
