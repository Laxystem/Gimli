/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class Username(id: EntityID<Long>) : LongEntity(id) {
    val profile by Table.profile referencingNullable Profile
    val instance by Table.instance
    val value by Table.value

    companion object : LongEntityClass<Username>(Table)

    object Table : LongIdTable() {
        val profile = nullableReference(name = "profile_id", Profile.Table, onDelete = ReferenceOption.SET_NULL)
        val instance = domain(name = "instance").nullable()
        val value = citext(name = "username")

        init {
            uniqueIndex(instance, value) // TODO: NULLS NOT DISTINCT
        }
    }
}
