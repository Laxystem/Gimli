package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class Message(id: EntityID<Long>) : LongEntity(id) {
    val author by Table.author referencing Profile
    val content by Table.content
    val creationTime by Table.creationTime
    val quotedMessage by Table.quotedMessage referencingNullable Message
    val railway by Table.railway referencingNullable Railway

    companion object : LongEntityClass<Message>(Table)
    object Table : LongIdTable() {
        val author = reference(name = "author_profile_id", Profile.Table, onDelete = ReferenceOption.CASCADE)
        val content = text(name = "content")
        val creationTime = creationTime()
        val quotedMessage = nullableReference(name = "quoted_message_id", foreign = this, onDelete = ReferenceOption.CASCADE)
        val railway = nullableReference(name = "railway_id", Railway.Table, onDelete = ReferenceOption.SET_NULL)
    }
}
