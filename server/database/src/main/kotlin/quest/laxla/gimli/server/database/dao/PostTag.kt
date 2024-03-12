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

class PostTag(id: EntityID<Long>) : LongEntity(id) {
    val post by Table.post referencing Post
    val tag by Table.tag referencing Tag

    companion object : LongEntityClass<PostTag>(Table)
    object Table : LongIdTable() {
        val post = reference(name = "post_id", Post.Table, onDelete = ReferenceOption.CASCADE)
        val tag = reference(name = "tag_id", Tag.Table, onDelete = ReferenceOption.RESTRICT)

        init {
            uniqueIndex(post, tag)
        }
    }
}
