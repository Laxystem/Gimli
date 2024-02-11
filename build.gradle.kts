import com.dorongold.gradle.tasktree.TaskTreePlugin
import dev.yumi.gradle.licenser.YumiLicenserGradlePlugin

plugins {
    id("dev.yumi.gradle.licenser")
    id("com.dorongold.task-tree") version "2.1.1"
}

allprojects {
    apply<YumiLicenserGradlePlugin>()
    apply<TaskTreePlugin>()

    version = project.properties["version"]!!
    group = "quest.laxla"

    repositories.mavenCentral()

    license {
        rule(rootProject.file("HEADER.txt"))
        include("**/*.kt")
    }
}
