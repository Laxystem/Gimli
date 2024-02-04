package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class Authorizable(id: EntityID<Long>) : LongEntity(id) {
    val identifier by Table.identifier referencing FederalIdentifier

    companion object : LongEntityClass<Authorizable>(Table)
    object Identifier : FederalIdentification.Companion<Authorizable>(Authorizable)
    object Table : LongIdTable() {
        val identifier = reference(name = "identifier_id", FederalIdentifier.Table, onDelete = ReferenceOption.RESTRICT)
    }
}
