import dev.yumi.gradle.licenser.YumiLicenserGradlePlugin

plugins {
    id("dev.yumi.gradle.licenser")
}

allprojects {
    apply<YumiLicenserGradlePlugin>()

    version = project.properties["version"]!!
    group = "quest.laxla"

    repositories.mavenCentral()

    license {
        rule(rootProject.file("HEADER.txt"))
        include("**/*.kt")
    }
}
