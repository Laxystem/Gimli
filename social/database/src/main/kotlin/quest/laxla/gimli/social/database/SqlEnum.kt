/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.social.database

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.StringColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import kotlin.enums.EnumEntries
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObjectInstance

abstract class SqlEnum<E>(
    entries: EnumEntries<E>,
    val sqlType: String
) : StringColumnType() where E : Enum<E> {
    private val enumConstants: Map<String, E> by lazy { entries.associateBy { it.name } }

    init {
        require(sqlType.isNotBlank())
    }

    fun Transaction.create() {
        exec("""
            CREATE TYPE IF NOT EXISTS $sqlType AS ENUM ();
            
            ${enumConstants.keys.joinToString(separator = "\n") { "ALTER TYPE $sqlType ADD VALUE IF NOT EXISTS $it;" }};
            """.trimIndent()
        )
    }

    operator fun KProperty<Boolean>.provideDelegate(thisRef: Any?, property: KProperty<*>): Lazy<ImmutableList<E>> =
        lazy {
            enumConstants.values.filter { getter.call(it) }.toImmutableList()
        }

    override fun sqlType(): String = sqlType

    @Suppress("UNCHECKED_CAST")
    override fun valueFromDB(value: Any) = when (value) {
        is String -> enumConstants[value]
            ?: error("'$value' can't be associated with enum '$sqlType' (${this::class.qualifiedName}).")

        is Enum<*> -> value as E
        else -> error("'$value' of ${value::class.qualifiedName} is not valid for enum '$sqlType' (${this::class.qualifiedName}).")
    }

    override fun notNullValueToDB(value: Any): Any = when (value) {
        is String -> super.notNullValueToDB(value)
        is Enum<*> -> super.notNullValueToDB(value.name)
        else -> error("'$value' of ${value::class.qualifiedName} is not valid for enum '$sqlType' (${this::class.qualifiedName}).")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as SqlEnum<*>

        if (enumConstants != other.enumConstants) return false
        if (sqlType != other.sqlType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + enumConstants.hashCode()
        result = 31 * result + sqlType.hashCode()
        return result
    }
}


fun <E> Table.enum(enum: SqlEnum<E>, name: String): Column<E> where E : Enum<E> = registerColumn(name, enum)


/**
 * Creates an enumeration column of [E], where its [SqlEnum] instance is its companion object.
 */
@Suppress("UNCHECKED_CAST")
@Throws(ClassCastException::class)
inline fun <reified E> Table.enum(name: String): Column<E> where E : Enum<E> =
    enum(E::class.companionObjectInstance!! as SqlEnum<E>, name)
