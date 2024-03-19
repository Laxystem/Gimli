import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    gimli
}

val collections: String by properties
val datetime: String by properties
val jdk: String by properties
val klogging: String by properties
val ktor: String by properties
val logback: String by properties
val serialization: String by properties
val uri: String by properties

kotlin {
    jvm()

    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        binaries.library()
    }

    sourceSets.commonMain.dependencies {
        api("com.eygraber:uri-kmp:$uri")
        api("io.github.oshai:kotlin-logging:$klogging")
        api("org.jetbrains.kotlinx:kotlinx-datetime:$datetime")
        api("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialization")
        api("org.jetbrains.kotlinx:kotlinx-collections-immutable:$collections")
    }

    sourceSets.commonTest.dependencies {
        implementation(kotlin("test"))
    }

    sourceSets.jvmMain.dependencies {
        runtimeOnly("ch.qos.logback:logback-classic:$logback")
    }
}
