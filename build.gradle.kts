import dev.yumi.gradle.licenser.YumiLicenserGradlePlugin
import kotlinx.kover.gradle.plugin.KoverGradlePlugin
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("dev.yumi.gradle.licenser")
    id("org.jetbrains.dokka")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
    id("org.jetbrains.kotlinx.kover")
    kotlin("jvm") // only used for dokka
    publishing
}

buildscript {
    val dokka: String by project

    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:$dokka")
    }
}

tasks.build {
    dependsOn(tasks.applyLicenses)
}

apiValidation {
    apiDumpDirectory = "abi"
    ignoredProjects += project.name
}

allprojects {
    apply<YumiLicenserGradlePlugin>()
    apply<DokkaPlugin>()
    apply<KoverGradlePlugin>()
    apply<MavenPublishPlugin>()

    version = rootProject.properties["version"]!!
    group = "quest.laxla.gimli"

    repositories.mavenCentral()

    license {
        rule(rootProject.file("HEADER.txt"))
        include("**/*.kt")
    }

    subprojects.forEach {
        if (it.subprojects.isNotEmpty()) tasks.dokkaHtmlMultiModule {
            dependsOn(it.tasks.dokkaHtmlMultiModule)
        }

        dependencies.kover(it)
    }

    tasks.withType<DokkaTask>().configureEach {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "Copyright © 2024 Project Gimli and Contributors. Licensed MPL 2.0."
        }
    }

    publishing {
        repositories {
            maven(url = "https://codeberg.org/api/packages/${System.getenv("CI_REPO_OWNER")}/maven") {
                name = "Codeberg"

                credentials(HttpHeaderCredentials::class) {
                    name = "Authorization"
                    value = "token ${System.getenv("CODEBERG_CREATE_PACKAGES_TOKEN")}"
                }

                authentication {
                    val header by creating(HttpHeaderAuthentication::class)
                }
            }
        }
    }
}
