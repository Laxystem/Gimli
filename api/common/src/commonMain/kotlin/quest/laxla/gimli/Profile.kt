package quest.laxla.gimli

import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.emptyString

public sealed interface Profile {
    public val displayName: String?
    public val title: String?
    public val about: String?

    public interface Actual : Profile, Element.Federalized<Actual> {
        public val accessor: Accessor
        public val authorizable: Authorizable
        override val displayName: String
        override val title: String
        override val about: String
    }

    @Serializable
    public data class CreateBuilder(
        val owner: Accessor,
        override var displayName: String = emptyString(),
        override var title: String = emptyString(),
        override var about: String = emptyString()
    ) : Profile, Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    @Serializable
    public data class UpdateBuilder(
        override val idAtHomeInstance: Long,
        override var displayName: String? = null,
        override var title: String? = null,
        override var about: String? = null
    ): Profile, Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
