import dev.yumi.gradle.licenser.YumiLicenserGradlePlugin
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("dev.yumi.gradle.licenser")
    id("org.jetbrains.dokka")
    kotlin("jvm") // only used for dokka
}

buildscript {
    val dokka: String by properties

    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:$dokka")
    }
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

    tasks.withType<DokkaTask>().configureEach {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "Copyright © 2024 Project Gimli and Contributors. Licensed MPL 2.0."
        }
    }
}
