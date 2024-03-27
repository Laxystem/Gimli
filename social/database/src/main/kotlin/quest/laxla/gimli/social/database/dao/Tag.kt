/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.social.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.social.database.nullableReference
import quest.laxla.gimli.social.database.referencingNullable

class Tag(id: EntityID<Long>) : LongEntity(id) {
    val name by Table.name
    val guild by Table.guild referencingNullable Guild

    companion object : LongEntityClass<Tag>(Table)
    object Table : LongIdTable() {
        val name = varchar(name = "name", length = 72)
        val guild = nullableReference(name = "guild_id", Guild.Table, onDelete = ReferenceOption.SET_NULL)
    }
}
