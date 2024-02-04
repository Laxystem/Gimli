package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class Guild(id: EntityID<Long>) : LongEntity(id) {
    val profile by Table.profile referencing Profile
    val defaultRailwayAuthorizable by Table.defaultRailwayAuthorizable referencing Authorizable

    companion object : LongEntityClass<Guild>(Table)
    object Table : LongIdTable() {
        val profile = reference(name = "profile_id", Profile.Table, onDelete = ReferenceOption.RESTRICT).uniqueIndex()
        val defaultRailwayAuthorizable = reference(name = "default_railway_authorizable_id", Authorizable.Table, onDelete = ReferenceOption.RESTRICT).uniqueIndex()
    }
}
