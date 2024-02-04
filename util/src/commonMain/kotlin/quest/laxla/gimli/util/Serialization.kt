package quest.laxla.gimli.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val GimliJson = Json {
    encodeDefaults = false
    explicitNulls = false
    ignoreUnknownKeys = true
    decodeEnumsCaseInsensitive = true
}
