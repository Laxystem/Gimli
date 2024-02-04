allprojects {
    repositories {
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental") {
            name = "Kotlin Wasm Experimental"
        }
    }

    version = project.properties["version"]!!
    group = "quest.laxla"
}
