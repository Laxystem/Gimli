package quest.laxla.gimli.server.impl

import quest.laxla.gimli.Authorizable
import quest.laxla.gimli.Element
import quest.laxla.gimli.server.database.dao.Authorizable as AuthorizableDao

object AuthorizableProvider : ElementProvider<Authorizable, AuthorizableDao> {

    private data class Impl(override val idAtLocalInstance: Long) : Authorizable {
        override val provider: Element.Provider<Authorizable>
            get() = AuthorizableProvider
    }

    override fun AuthorizableDao.convert(): Authorizable = Impl(id.value)

    override fun get(idAtHomeInstance: Long): Authorizable = AuthorizableDao[idAtHomeInstance].convert()
}
