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
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class MessageMention(id: EntityID<Long>) : LongEntity(id) {
    val mentionedProfile by Table.mentionedProfile referencing Profile
    val message by Table.message referencing Message

    companion object : LongEntityClass<MessageMention>(Table)
    object Table : LongIdTable() {
        val mentionedProfile = reference(name = "mentioned_profile_id", Profile.Table, onDelete = ReferenceOption.CASCADE)
        val message = reference(name = "message_id", Message.Table, onDelete = ReferenceOption.CASCADE)
    }
}
