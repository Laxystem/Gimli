package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class Accessor(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Accessor>(Table)
    class Identifier : FederalIdentification.Companion<Accessor>(Accessor)
    object Table : LongIdTable()
}
