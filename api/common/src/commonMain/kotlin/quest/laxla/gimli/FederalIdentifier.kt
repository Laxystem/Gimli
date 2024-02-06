package quest.laxla.gimli

import quest.laxla.gimli.util.removePrefix
import quest.laxla.gimli.util.substringBefore

public object FederalIdentifier {
    public const val Https: String = "https://"
    public const val Acct: String = "acct:"

    public val IriPrefixRegex: Regex = "(web\\+)?[a-z]+:(//)?".toRegex()
    public val PathStarterRegex: Regex = "[#/?:]".toRegex()

    public fun ofDomain(domain: String): String = "$Https$domain"

    public fun ofApi(domain: String): String = ofDomain(domain) + "/api"

    private fun ofAuth(domain: String) = ofApi(domain) + "/auth"

    public fun ofAuthorization(domain: String): String = ofAuth(domain) + "/orization"

    public fun ofAuthentication(domain: String): String = ofAuth(domain) + "/entication"


    public infix fun String.isOfDomain(domain: String): Boolean = domain.extractDomain() ==
            if (startsWith(prefix = Acct, ignoreCase = true)) substringAfter(delimiter = '@').lowercase()
            else extractDomain()


    public fun String.extractDomain(): String =
        lowercase().removePrefix(IriPrefixRegex).substringBefore(PathStarterRegex)
}