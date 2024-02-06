package quest.laxla.gimli

/**
 * An [Account], [Profile] or anything else capable using [Authorizable]s' [abilities][Ability].
 */
public interface Accessor : Element.Federalized<Accessor> {
    public companion object : Element.Informer {
        override fun federalIdentifierFor(numeralIdentifier: Long, domain: String): String =
            FederalIdentifier.ofAuthorization(domain) + "/accessor/$numeralIdentifier"
    }
}
