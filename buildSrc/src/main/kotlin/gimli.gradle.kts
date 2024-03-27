import org.intellij.lang.annotations.Language
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinWasmJsTargetDsl

plugins {
    id("dev.yumi.gradle.licenser")
    id("org.jetbrains.dokka")
    id("org.jetbrains.kotlinx.kover")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    publishing
}

version = project.properties["version"]!!
group = "quest.laxla.gimli"

tasks {
    val createReadme by creating {

        val descriptionFile = file(path = "module.md")
        inputs.file(descriptionFile).normalizeLineEndings().optional()

        val readme = file(path = "README.md")
        outputs.file(readme)

        val parentModuleName = run {
            val name = mutableListOf<String>()

            var parent = parent
            while (parent != null) {
                name += parent.properties["moduleName"]?.toString() ?: parent.name
                parent = parent.parent
            }

            name.asReversed().joinToString(separator = " ")
        }

        @Language("Markdown") val description =
            if (descriptionFile.exists() && descriptionFile.isFile && descriptionFile.length() != 0L) descriptionFile.readText()
                .trim()
            else "<p style=\"text-align: center\"><i>This module's documentation is empty.</i></p>"

        val moduleMavenPackage: Boolean? by project
        val moduleMavenRepository: String by project


        @Language("Markdown") val artifact = if (moduleMavenPackage != false) """
            ## Artifacts
            
            <details><summary>Gradle Kotlin</summary>
            
            ```kotlin
            repositories {
                maven(url = "$moduleMavenRepository")
            }
            
            dependencies {
                implementation("${project.group}:${project.name}:$version")
            }
            ```
            
            </details>
            <details><summary>Gradle Groovy</summary>
            
            ```groovy
            repositories {
                maven url: "$moduleMavenRepository"
            }
            
            dependencies {
                implementation "${project.group}:${project.name}:$version"
            }
            ```
            
            </details>
            <details><summary>Maven</summary>
            
            ```xml
            <repositories>
            	<repository>
            		<id>moria</id>
            		<url>$moduleMavenRepository</url>
            	</repository>
            </repositories>

            <distributionManagement>
            	<repository>
            		<id>moria</id>
            		<url>$moduleMavenRepository</url>
            	</repository>

            	<snapshotRepository>
            		<id>moria</id>
            		<url>$moduleMavenRepository</url>
            	</snapshotRepository>
            </distributionManagement>
            
            <dependencies>
                <dependency>
                	<groupId>${project.group}</groupId>
                	<artifactId>${project.name}</artifactId>
                	<version>$version</version>
                </dependency>
            </dependencies>
            ```
            
            </details>
        """.trimIndent() else ""

        val subprojects = buildString {
            if (project.subprojects.isEmpty()) return@buildString

            appendLine("## Subprojects")

            for (subproject in subprojects) {
                if (subproject.parent !== project) continue

                val moduleName: String? by subproject
                val slug = subproject.name.substringAfter(project.name + '-')
                val name = moduleName?.let { "$it (`$slug`)" } ?: "`slug`"

                val moduleShortDescription: String? by subproject
                val shortDescription = moduleShortDescription?.let { " – $it" } ?: ""

                append("* [$name]($slug/README.md)$shortDescription\n")
            }
        }

        val moduleName: String by project

        //language=markdown
        project.file(readme).writeText(
            text = """
<!-- This file is auto-generated. Edit module.md instead. -->
# [$parentModuleName](../README.md) $moduleName

$description

$artifact

$subprojects
""".trimIndent()
        )
    }

    val organize: Task by creating {
        dependsOn(applyLicenses)
        dependsOn(createReadme)
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

repositories.mavenCentral()

val jdk: String by project

kotlin {
    applyDefaultHierarchyTemplate()
    withSourcesJar()
    jvmToolchain(jdk.toInt())

    @OptIn(ExperimentalKotlinGradlePluginApi::class) compilerOptions {
        allWarningsAsErrors = true
        explicitApi = ExplicitApiMode.Strict
        verbose = true

        freeCompilerArgs.add("-Xjvm-default=all")
    }

    targets.withType<KotlinWasmJsTargetDsl> {
        browser()
        nodejs()
        d8()
    }
}

license {
    rule(rootProject.file("HEADER.txt"))
    include("**/*.kt")
}

private val dokkaTaskName = "dokkaHtmlMultiModule"

parent?.let { parent ->
    try {
        tasks.named(dokkaTaskName) {
            dependsOn(parent.tasks.named(dokkaTaskName))
        }
    } catch (e: UnknownTaskException) {
        logger.info("Cannot find task $dokkaTaskName, skipping.", e)
    }

    parent.dependencies { kover(project) }
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
