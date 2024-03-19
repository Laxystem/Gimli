import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    gimli
}

kotlin {
    jvm()

    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        binaries.executable()
        browser()
        d8()
        nodejs()
    }

    sourceSets.commonMain.dependencies {
        api(gimliModule(":core"))
    }
}
