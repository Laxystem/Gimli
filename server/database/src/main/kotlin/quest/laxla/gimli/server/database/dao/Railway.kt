package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class Railway(id: EntityID<Long>) : LongEntity(id) {
    val guild by Table.guild referencing Guild

    companion object : LongEntityClass<Railway>(Table)
    object Table : LongIdTable() {
        val guild = reference(name = "guild_id", Guild.Table, onDelete = ReferenceOption.CASCADE)
    }
}
