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
import quest.laxla.gimli.server.database.creationTime
import quest.laxla.gimli.server.database.dao.Follow.Companion.referrersOn
import quest.laxla.gimli.server.database.reference
import quest.laxla.gimli.server.database.referencing

class PostRevision(id: EntityID<Long>): LongEntity(id) {
    val post by Table.post referencing Post
    val title by Table.title
    val summary by Table.summary
    val content by Table.content
    val locale by Table.locale
    val creationTime by Table.creationTime

    companion object : LongEntityClass<PostRevision>(Table)
    object Table : LongIdTable() {
        val post = reference(name = "post_id", Post.Table, onDelete = ReferenceOption.CASCADE)
        val title = text(name = "title")
        val summary = text(name = "summary")
        val content = text(name = "content")
        val locale = varchar(name = "locale", length = 35)
        val creationTime = creationTime()
    }
}
