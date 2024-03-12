package quest.laxla.gimli.server.database

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import quest.laxla.gimli.server.database.dao.FederalIdentifier
import quest.laxla.gimli.util.Uri

abstract class FederalEntity(id: EntityID<Long>, table: Table) : LongEntity(id) {
    val externalIdentifier by mapping(table.externalIdentifier, Uri::parse)

    abstract class Table(
        name: String = "",
        idColumnName: String = "id",
        externalIdentifierColumnName: String = "external_identifier"
    ) : LongIdTable(name, idColumnName) {
        val externalIdentifier =
            FederalIdentifier.Table.varchar(externalIdentifierColumnName, length = 4096).nullable().uniqueIndex()
    }
}
