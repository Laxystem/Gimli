/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project: Gimli and contributors.
 */

package quest.laxla.gimli.server.database

import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import quest.laxla.gimli.util.env

val databaseDriver by env(default = "com.impossibl.postgres.jdbc.PGDriver")
val databaseDomain by env(default = "localhost:5432")
val databaseName by env(default = "postgres")
val databaseUsername by env(default = "postgres")
private val databasePassword by env(default = "postgres")

val database by lazy {
    Database.connect(
        url = "jdbc:pgsql://$databaseDomain/$databaseName",
        driver = databaseDriver,
        user = databaseUsername,
        password = databasePassword
    )
}

suspend fun <T> Database.transaction(statement: suspend Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.Default, db = this, statement = statement)

fun Table.domain(name: String = "domain") = varchar(name = name, length = 255)
fun Table.creationTime() = timestamp(name = "created_at").default(Clock.System.now())

fun Table.instance(name: String = "instance") = domain(name).nullable()
fun Table.externalID(name: String = "external_id") = long(name).nullable()

fun Table.uniqueIndexOrBothNull(a: Column<in Nothing?>, b: Column<in Nothing?>) {
    uniqueIndex(a, b)
    check { a.isNull() eq b.isNull() }
}

fun <T : Comparable<T>> Table.nullableReference(
    name: String,
    foreign: IdTable<T>,
    onDelete: ReferenceOption? = null,
    onUpdate: ReferenceOption? = null,
    fkName: String? = null
): Column<EntityID<T>?> = optReference(name, foreign, onDelete, onUpdate, fkName)

fun <T : Comparable<T>> Table.nullableReference(
    name: String,
    foreign: IdTable<T>,
    onDelete: ReferenceOption? = null,
    onUpdate: ReferenceOption? = null,
    fkName: String? = null,
    check: SqlExpressionBuilder.() -> Op<Boolean>
): Column<EntityID<T>?> = nullableReference(name, foreign, onDelete, onUpdate, fkName)
    .check { it.isNull() or exists(foreign.select { it eq foreign.id }.andWhere(check)) }

fun <T : Comparable<T>> Table.reference(
    name: String,
    foreign: IdTable<T>,
    onDelete: ReferenceOption? = null,
    onUpdate: ReferenceOption? = null,
    fkName: String? = null,
    check: SqlExpressionBuilder.() -> Op<Boolean>
): Column<EntityID<T>> = reference(name, foreign, onDelete, onUpdate, fkName).check {
    exists(foreign.select { it eq foreign.id }.andWhere(check))
}

infix fun <REF, ID, T> Column<REF>.referencing(entity: EntityClass<ID, T>) where REF : Comparable<REF>, ID : Comparable<ID>, T : Entity<ID> =
    entity referencedOn this

infix fun <REF, ID, T> Column<REF?>.referencingNullable(entity: EntityClass<ID, T>) where REF : Comparable<REF>, ID : Comparable<ID>, T : Entity<ID> =
    entity optionalReferencedOn this

fun Table.citext(name: String, collate: String? = null) =
    registerColumn<String>(name, object : StringColumnType(collate) {
        override fun sqlType(): String = "CITEXT"
    })
