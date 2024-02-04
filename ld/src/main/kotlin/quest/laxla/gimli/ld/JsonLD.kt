package quest.laxla.gimli.ld

import com.apicatalog.jsonld.http.DefaultHttpClient
import com.apicatalog.jsonld.loader.HttpLoader
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

const val OutputContentType = "application/ld+json;profile=\"https://www.w3.org/ns/activitystreams\""
const val AcceptedContentTypes = "$OutputContentType,application/activity+json;q=0.9"

internal val loader = HttpLoader { targetUri, _ ->
    DefaultHttpClient.defaultInstance().send(targetUri, AcceptedContentTypes)
}

