package quest.laxla.gimli

public sealed interface Guild {
    public interface Actual : Element.Federalized<Actual> {
        public val profile: Profile

        /**
         * The default [Railway] permissions.
         *
         * Guilds also have additional, unique permissions that are stored in this property.
         * Permissions for the Guild's anonymous profile can be found at [profile].[authorizable][Profile.Actual.authorizable].
         */
        public val authorizable: Authorizable
    }

    public data object CreateBuilder : Guild, Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = this
    }

    public data class UpdateBuilder(override val idAtHomeInstance: Long) : Guild, Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
