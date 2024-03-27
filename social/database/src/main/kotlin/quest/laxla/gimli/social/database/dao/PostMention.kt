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
import quest.laxla.gimli.social.database.referencing

class PostMention(id: EntityID<Long>) : LongEntity(id) {
    val mentionedProfile by Table.mentionedProfile referencing Profile
    val post by Table.post referencing Post

    companion object : LongEntityClass<PostMention>(Table)
    object Table : LongIdTable() {
        val mentionedProfile = reference(name = "mentioned_profile_id", Profile.Table, onDelete = ReferenceOption.CASCADE)
        val post = reference(name = "post_id", Post.Table, onDelete = ReferenceOption.CASCADE)

        init {
            uniqueIndex(mentionedProfile, post)
        }
    }
}
