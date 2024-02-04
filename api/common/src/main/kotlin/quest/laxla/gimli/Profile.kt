package quest.laxla.gimli

import kotlinx.serialization.Serializable

sealed interface Profile {
    val displayName: String?
    val title: String?
    val about: String?

    interface Actual : Profile, Element.Federalized<Actual> {
        val accessor: Accessor
        val authorizable: Authorizable
        override val displayName: String
        override val title: String
        override val about: String
    }

    @Serializable
    data class CreateBuilder(
        val owner: Accessor,
        override var displayName: String = "",
        override var title: String = "",
        override var about: String = ""
    ) : Profile, Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    @Serializable
    data class UpdateBuilder(
        override val idAtHomeInstance: Long,
        override val displayName: String? = null,
        override val title: String? = null,
        override val about: String? = null
    ): Profile, Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
