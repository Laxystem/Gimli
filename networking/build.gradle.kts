import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    gimli
}

kotlin {
    jvm()

    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        binaries.library()
    }

    sourceSets.commonMain.dependencies {
        api(gimliModule(":core"))
    }
}
