/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.social.database.dao

import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.social.database.*

class Post(id: EntityID<Long>) : FederalEntity(id, Table) {
    val author by Table.author referencing Profile
    val contentType by Table.contentType
    val quotedPost by Table.quotedPost referencingNullable Post
    val railway by Table.railway referencingNullable Railway
    val creationTime by Table.creationTime

    val mentionedProfiles by Profile via PostMention.Table
    val tags by Tag via PostTag.Table

    companion object : LongEntityClass<Post>(Table)
    object Table : FederalEntity.Table() {
        val author = reference(name = "author_profile_id", Profile.Table, onDelete = ReferenceOption.CASCADE)
        val creationTime = creationTime()
        val quotedPost = nullableReference(name = "quoted_post_id", foreign = this, onDelete = ReferenceOption.CASCADE)
        val railway = nullableReference(name = "railway_id", Railway.Table, onDelete = ReferenceOption.SET_NULL)
        val contentType = varchar(name = "content_type", length = 255).default("html")
    }
}
