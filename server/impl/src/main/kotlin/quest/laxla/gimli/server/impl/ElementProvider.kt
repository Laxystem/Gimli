package quest.laxla.gimli.server.impl

import quest.laxla.gimli.Element


internal interface ElementProvider<out T, in Dao> : Element.Provider<T> where T : Element<T> {
    fun Dao.convert(): T
}
