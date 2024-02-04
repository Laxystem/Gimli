package quest.laxla.gimli.server.impl

import quest.laxla.gimli.Accessor
import quest.laxla.gimli.Authorizable
import quest.laxla.gimli.Element
import quest.laxla.gimli.Profile
import quest.laxla.gimli.server.impl.AccessorProvider.convert
import quest.laxla.gimli.server.impl.AuthorizableProvider.convert
import quest.laxla.gimli.server.database.dao.Profile as ProfileDao

object ProfileProvider : ElementProvider<Profile.Actual, ProfileDao>, Element.Provider.Federal<Profile.Actual> {
    private data class Impl(
        override val idAtHomeInstance: Long,
        override val accessor: Accessor,
        override val authorizable: Authorizable,
        override val displayName: String,
        override val title: String,
        override val about: String
    ) : Profile.Actual {
        override val provider: Element.Provider<Profile.Actual>
            get() = ProfileProvider
    }

    override fun ProfileDao.convert(): Profile.Actual =
        Impl(id.value, accessor.convert(), authorizable.convert(), displayName, title, about)

    override fun get(idAtHomeInstance: Long): Profile.Actual =
        ProfileDao[idAtHomeInstance].convert()

    override fun get(federalIdentifier: String): Profile.Actual {
        TODO("Not yet implemented")
    }
}
