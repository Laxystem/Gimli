/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project: Gimli and contributors.
 */

package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class MessageTag(id: EntityID<Long>) : LongEntity(id) {
    val message by Table.message referencing Message
    val tag by Table.tag referencing Tag

    companion object : LongEntityClass<MessageTag>(Table)
    object Table : LongIdTable() {
        val message = reference(name = "message_id", Message.Table, onDelete = ReferenceOption.CASCADE)
        val tag = reference(name = "tag_id", Tag.Table, onDelete = ReferenceOption.RESTRICT)
    }
}
