/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class ProfileIdentifier(id: EntityID<Long>) : LongEntity(id) {
    val profile by Table.profile referencingNullable Profile
    val value by Table.value
    val isPrimary by Table.isPrimary

    companion object : LongEntityClass<ProfileIdentifier>(Table)

    object Table : LongIdTable() {
        val profile = nullableReference(name = "profile_id", Profile.Table, onDelete = ReferenceOption.SET_NULL)
        val value = varchar(name = "identifier", length = 4096)
        val isPrimary = bool(name = "is_primary").defaultExpression(Op.FALSE)

        init {
            uniqueIndex(profile) { isPrimary eq Op.TRUE }
        }
    }
}
