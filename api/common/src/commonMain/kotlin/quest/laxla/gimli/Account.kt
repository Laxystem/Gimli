package quest.laxla.gimli

import kotlinx.serialization.Serializable

public interface Account : Element<Account> {
    @Serializable
    public data object CreateBuilder : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = this
    }

    @Serializable
    public data class UpdateBuilder(override val primaryFederalIdentifier: String) : Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = this
    }

    public companion object : Element.Informer {
        override fun federalIdentifierFor(numeralIdentifier: Long, domain: String): String =
            FederalIdentifier.ofAuthentication(domain) + "/account/$numeralIdentifier"
    }
}
