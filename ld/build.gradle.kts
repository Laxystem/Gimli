plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val collections: String by properties
val datetime: String by properties
val jakarta: String by properties
val ktor: String by properties
val okio: String by properties
val serialization: String by properties
val titanium: String by properties

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetime")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
    api(gimliModule(":util"))
    implementation("com.apicatalog:titanium-json-ld-jre8:$titanium")
    implementation("com.squareup.okio:okio-jvm:$okio")
    implementation("org.glassfish:jakarta.json:$jakarta")
}
