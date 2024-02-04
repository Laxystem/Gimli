package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.notExists
import org.jetbrains.exposed.sql.select
import quest.laxla.gimli.server.database.*

class Profile(id: EntityID<Long>) : LongEntity(id) {
    val about by Table.about
    val accessor by Table.accessor referencing Accessor
    val authorizable by Table.accessor referencing Authorizable
    val creationTime by Table.creationTime
    val displayName by Table.displayName
    val title by Table.title


    companion object : LongEntityClass<Profile>(Table)
    object Table : LongIdTable() {
        val title = text(name = "title")
        val about = text(name = "about")
        val accessor = reference(name = "accessor_id", Accessor.Table, ReferenceOption.RESTRICT).uniqueIndex()
        val authorizable = reference(name = "authorizable_id", Authorizable.Table, ReferenceOption.RESTRICT).uniqueIndex()
        val creationTime = creationTime()
        val displayName = varchar(name = "display_name", length = 32)
    }
}
