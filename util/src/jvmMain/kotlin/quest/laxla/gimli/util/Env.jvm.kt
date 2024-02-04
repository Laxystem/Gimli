package quest.laxla.gimli.util

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import java.io.File
import java.util.*

private val envVariableFiles = persistentListOf(".env", "local.properties")

public actual fun fetchEnvironmentVariables(
    defaults: Map<String, String>
): ImmutableMap<String, String> {
    val properties = Properties(System.getProperties())
    properties.putAll(defaults)

    envVariableFiles.forEach { filename ->
        File(filename).inputStream().use {
            properties.load(it)
        }
    }

    return properties.map { (key, value) -> key.toString() to value.toString() }.toTypedArray().let(::persistentMapOf)
}

