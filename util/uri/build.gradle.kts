import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    gimli
}

val uri: String by project

kotlin {
    jvm()

    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        binaries.library()
    }

    sourceSets.commonMain.dependencies {
        api(gimliModule(":util"))
        api("com.eygraber:uri-kmp:$uri")
    }
}
