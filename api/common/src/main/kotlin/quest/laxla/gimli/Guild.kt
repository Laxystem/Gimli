package quest.laxla.gimli

sealed interface Guild {
    interface Actual : Element.Federalized<Actual> {
        val profile: Profile

        /**
         * The default [Railway] permissions.
         *
         * Guilds also have additional, unique permissions that are stored in this property.
         * Permissions for the Guild's anonymous profile can be found at [profile].[authorizable][Profile.Actual.authorizable].
         */
        val authorizable: Authorizable
    }

    data object CreateBuilder : Guild, Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = this
    }

    data class UpdateBuilder(override val idAtHomeInstance: Long) : Guild, Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
