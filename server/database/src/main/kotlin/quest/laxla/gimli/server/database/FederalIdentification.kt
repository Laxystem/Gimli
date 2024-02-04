package quest.laxla.gimli.server.database

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.dao.FederalIdentifier

class FederalIdentification<T> private constructor(
    id: EntityID<Long>,
    identifiedEntityClass: LongEntityClass<T>,
    table: Table
) : LongEntity(id) where T : LongEntity {
    val federalIdentifier by table.federalIdentifier referencing FederalIdentifier
    val value by table.value referencing identifiedEntityClass

    private class Table(
        identifiedTable: IdTable<Long>,
        name: String,
        idColumnName: String,
        federalIdentifierColumnName: String
    ) : LongIdTable(name = name + "_federal_identifier", idColumnName) {
        val federalIdentifier =
            reference(name = federalIdentifierColumnName, FederalIdentifier.Table, onDelete = ReferenceOption.RESTRICT)
        val value = reference(name = name + "_id", identifiedTable, onDelete = ReferenceOption.CASCADE)
    }

    abstract class Companion<T> private constructor(
        identifiedEntityClass: LongEntityClass<T>,
        table: Table,
    ) : LongEntityClass<FederalIdentification<T>>(
        table = table,
        entityCtor = { FederalIdentification(it, identifiedEntityClass, table) }
    ) where T : LongEntity {
        constructor(
            identifiedEntityClass: LongEntityClass<T>,
            name: String = identifiedEntityClass.table.tableName,
            idColumnName: String = "id",
            federalIdentifierColumnName: String = "federal_identifier_id"
        ) : this(
            identifiedEntityClass,
            Table(identifiedEntityClass.table, name, idColumnName, federalIdentifierColumnName)
        )
    }
}
