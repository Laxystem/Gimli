import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

/**
 * Turns `:api:common` into `:api:api-common`.
 */
fun String.toGimliModule(): String {
    val elements = split(':')
    val result = mutableListOf<String>()

    elements.forEach {
        result += (result.asSequence() + it).filter(String::isNotEmpty).joinToString("-")
    }

    return result.joinToString(":")
}

fun DependencyHandler.gimliModule(path: String): Dependency = project(path.toGimliModule())
fun KotlinDependencyHandler.gimliModule(path: String): Dependency = project(path.toGimliModule())