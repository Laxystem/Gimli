package quest.laxla.gimli.server.impl

import quest.laxla.gimli.Accessor
import quest.laxla.gimli.Element
import quest.laxla.gimli.server.database.dao.Accessor as AccessorDao

object AccessorProvider : ElementProvider<Accessor, AccessorDao> {

    private data class Impl(override val idAtLocalInstance: Long) : Accessor {
        override val provider: Element.Provider<Accessor>
            get() = AccessorProvider
    }

    override fun AccessorDao.convert(): Accessor = Impl(id.value)

    override fun get(idAtHomeInstance: Long): Accessor = AccessorDao[idAtHomeInstance].convert()
}