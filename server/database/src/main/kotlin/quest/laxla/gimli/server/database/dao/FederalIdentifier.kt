package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

class FederalIdentifier(id: EntityID<Long>): LongEntity(id) {
    val value by Table.value

    companion object : LongEntityClass<FederalIdentifier>(Table)
    object Table : LongIdTable() {
        val value = varchar(name = "value", length = 4096).uniqueIndex()
    }
}
