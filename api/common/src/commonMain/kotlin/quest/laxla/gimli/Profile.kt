package quest.laxla.gimli

import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.emptyString

public sealed interface Profile : Element.Federalized<Profile> {
    public val accessor: Accessor
    public val authorizable: Authorizable
    public val displayName: String
    public val title: String
    public val about: String

    @Serializable
    public data class CreateBuilder(
        val owner: Ref<Accessor>,
        var displayName: String = emptyString(),
        var title: String = emptyString(),
        var about: String = emptyString()
    ) : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    @Serializable
    public data class UpdateBuilder(
        override val primaryFederalIdentifier: String,
        var displayName: String? = null,
        var title: String? = null,
        var about: String? = null
    ): Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
