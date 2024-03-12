package quest.laxla.gimli.server.impl

import org.jetbrains.exposed.dao.LongEntityClass
import quest.laxla.gimli.Element
import quest.laxla.gimli.server.database.FederalEntity
import quest.laxla.gimli.util.Uri
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
internal suspend inline fun <T, Dao> LocalProvider<T, Dao>.get(
    federalIdentifier: Uri,
    daoCompanion: LongEntityClass<Dao>,
    numeralId: () -> Long
): T where T : Element<T>, Dao : FederalEntity {
    contract {
        callsInPlace(numeralId, InvocationKind.AT_MOST_ONCE)
    }

    return if (instance.identifierManager.isLocal(federalIdentifier)) {
        val id = numeralId()

        instance.suspendTransaction { daoCompanion.findById(id) }
    } else {
        val table = daoCompanion.table as FederalEntity.Table
        val identifier = federalIdentifier.toString()

        instance.suspendTransaction { daoCompanion.find { table.externalIdentifier eq identifier }.singleOrNull() }
    }?.let(::convert) ?: throw NoSuchElementException("Federal identifier of [$federalIdentifier] unknown.")
}
