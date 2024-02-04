package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class EmailAddress(id: EntityID<Long>) : LongEntity(id) {
    val account by Table.account referencing Account
    val domain by Table.domain
    val isBackup by Table.isBackup
    val isVerified by Table.isVerified
    val localPart by Table.localPart

    companion object : LongEntityClass<EmailAddress>(Table)
    object Table : LongIdTable() {
        val account = reference(name = "account_id", Account.Table, onDelete = ReferenceOption.CASCADE)
        val domain = domain()
        val isBackup = bool(name = "is_backup").default(false)
        val isVerified = bool(name = "is_verified").default(false)
        val localPart = varchar(name = "local_part", length = 64)
    }
}
