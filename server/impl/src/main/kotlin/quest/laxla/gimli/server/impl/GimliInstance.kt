package quest.laxla.gimli.server.impl

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.transactionManager
import quest.laxla.gimli.Element
import quest.laxla.gimli.util.emptyPersistentMap
import kotlin.reflect.KClass

class GimliInstance internal constructor(
    val database: Database,
    val identifierManager: IdentifierManager,
    vararg providers: Pair<KClass<Any>, (GimliInstance) -> LocalProvider<*, *>>
) {
    private val providers = emptyPersistentMap<KClass<*>, LocalProvider<*, *>>().builder().apply {
        putAll(providers.asSequence().map { (kClass, provider) -> kClass to provider(this@GimliInstance) })
    }.build()

    @Suppress("UNCHECKED_CAST")
    internal operator fun <T, Dao> get(providerKClass: KClass<T>): LocalProvider<T, Dao> where T : Element<T> =
        providers[providerKClass] as LocalProvider<T, Dao>?
            ?: error("Provider of elements of type [$providerKClass] not registered.")

    internal inline fun <reified T, Dao> get(): LocalProvider<T, Dao> where T : Element<T> = this[T::class]

    internal suspend fun <T> suspendTransaction(statement: suspend Transaction.() -> T): T =
        newSuspendedTransaction(db = database, statement = statement)

    internal fun <T> blockingTransaction(readOnly: Boolean = false, statement: Transaction.() -> T): T =
        transaction(
            transactionIsolation = database.transactionManager.defaultIsolationLevel,
            readOnly = readOnly,
            db = database,
            statement = statement
        )
}