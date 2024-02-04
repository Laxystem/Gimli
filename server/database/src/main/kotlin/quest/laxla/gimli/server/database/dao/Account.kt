package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*


class Account(id: EntityID<Long>) : LongEntity(id) {
    val accessor by Table.accessor referencing Accessor
    val creationTime by Table.creationTime

    companion object : LongEntityClass<Account>(Table)
    object Table : LongIdTable() {
        val accessor = reference(name = "accessor_id", Accessor.Table, ReferenceOption.RESTRICT)
        val creationTime = creationTime()
    }
}
