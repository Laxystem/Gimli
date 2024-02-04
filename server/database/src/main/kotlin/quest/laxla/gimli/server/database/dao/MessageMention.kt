package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class MessageMention(id: EntityID<Long>) : LongEntity(id) {
    val mentionedProfile by Table.mentionedProfile referencing Profile
    val message by Table.message referencing Message

    companion object : LongEntityClass<MessageMention>(Table)
    object Table : LongIdTable() {
        val mentionedProfile = reference(name = "mentioned_profile_id", Profile.Table, onDelete = ReferenceOption.CASCADE)
        val message = reference(name = "message_id", Message.Table, onDelete = ReferenceOption.CASCADE)
    }
}
