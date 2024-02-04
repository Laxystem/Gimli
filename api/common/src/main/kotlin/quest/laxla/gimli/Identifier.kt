package quest.laxla.gimli

typealias DomainQuery = Map<String, Any?>

object Identifier {
    const val DOMAIN_PREFIX = "https://"
    const val LOCAL_DOMAIN = "localhost"
    const val QUERY = "?"
    const val QUERY_EQUALS = "="
    const val QUERY_SEPARATOR = ","
    const val SEPARATOR = "/"

    fun construct(domain: String = LOCAL_DOMAIN, vararg path: Any?, query: DomainQuery = emptyMap()) = buildString {
        append(DOMAIN_PREFIX)
        append(domain)

        path.forEach {
            append(SEPARATOR)
            append(it.toString())
        }

        query.takeIf(Map<String, Any?>::isNotEmpty)?.entries?.joinToString(prefix = QUERY,
            separator = QUERY_SEPARATOR,
            transform = { (key, entry) -> key + QUERY_EQUALS + entry.toString() })?.let(::append)
    }.lowercase()

    fun ofGuild(id: Long, domain: String = LOCAL_DOMAIN, vararg path: Any?, query: DomainQuery = emptyMap()) =
        construct(domain, "guild", id, *path, query = query)

    fun ofRailway(
        id: Long,
        guildID: Long,
        domain: String = LOCAL_DOMAIN,
        vararg path: Any?,
        query: DomainQuery = emptyMap()
    ) = ofGuild(guildID, domain, "railway", id, *path, query = query)

    fun ofProfile(
        id: Long, domain: String = LOCAL_DOMAIN, vararg path: Any?, query: DomainQuery = emptyMap()
    ) = construct(domain, "profile", id, *path, query = query)

    fun ofMessage(
        id: Long,
        domain: String = LOCAL_DOMAIN,
        vararg path: Any?,
        query: DomainQuery = emptyMap()
    ) = construct(domain, "message", id, *path, query)

    fun ofAccessor(
        id: Long,
        domain: String = LOCAL_DOMAIN,
        vararg path: Any?,
        query: DomainQuery = emptyMap()
    ) = construct(domain, "accessor", id, *path, query)

    fun ofAuthorizable(
        id: Long,
        domain: String = LOCAL_DOMAIN,
        vararg path: Any?,
        query: DomainQuery = emptyMap()
    ) = construct(domain, "authorizable", id, *path, query)
}
