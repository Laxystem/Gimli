import dev.yumi.gradle.licenser.YumiLicenserGradlePlugin
import org.jetbrains.dokka.gradle.DokkaPlugin

plugins {
    id("dev.yumi.gradle.licenser")
    id("org.jetbrains.dokka")
    kotlin("jvm") // only used for dokka
}

tasks.build {
    dependsOn(tasks.applyLicenses)
}

allprojects {
    apply<YumiLicenserGradlePlugin>()
    apply<DokkaPlugin>()

    version = project.properties["version"]!!
    group = "quest.laxla"

    repositories.mavenCentral()

    license {
        rule(rootProject.file("HEADER.txt"))
        include("**/*.kt")
    }

    subprojects.forEach {
        if (it.subprojects.isNotEmpty()) tasks.dokkaHtmlMultiModule {
            dependsOn(it.tasks.dokkaHtmlMultiModule)
        }
    }
}
