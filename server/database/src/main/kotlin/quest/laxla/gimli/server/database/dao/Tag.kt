package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class Tag(id: EntityID<Long>) : LongEntity(id) {
    val name by Table.name
    val guild by Table.guild referencingNullable Guild

    companion object : LongEntityClass<Tag>(Table)
    object Table : LongIdTable() {
        val name = varchar(name = "name", length = 72)
        val guild = nullableReference(name = "guild_id", Guild.Table, onDelete = ReferenceOption.SET_NULL)
    }
}
