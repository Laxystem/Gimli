plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

repositories.mavenCentral()

val exposed: String by properties
val postgres: String by properties
val serialization: String by properties

dependencies {
    api("org.jetbrains.exposed:exposed-core:$exposed")
    api("org.jetbrains.exposed:exposed-crypt:$exposed")
    api("org.jetbrains.exposed:exposed-dao:$exposed")
    api("org.jetbrains.exposed:exposed-jdbc:$exposed")
    api("org.jetbrains.exposed:exposed-kotlin-datetime:$exposed")
    api(gimliModule(":util:uri"))
    runtimeOnly("org.postgresql:postgresql:$postgres")
}
