package quest.laxla.gimli

import kotlinx.serialization.Serializable

public sealed interface Guild : Element.Federalized<Guild> {
    public val profile: Profile

    /**
     * The default [Railway] permissions.
     *
     * Guilds also have additional, unique permissions that are stored in this property.
     * Permissions for the Guild's anonymous profile can be found at [profile].[authorizable][Profile.Actual.authorizable].
     */
    public val authorizable: Authorizable

    @Serializable
    public data class CreateBuilder(var profile: Profile) : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    @Serializable
    public data class UpdateBuilder(override val primaryFederalIdentifier: String) : Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
