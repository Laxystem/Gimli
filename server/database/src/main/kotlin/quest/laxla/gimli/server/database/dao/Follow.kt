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

class Follow(id: EntityID<Long>) : LongEntity(id) {
    val isApproved by Table.isApproved
    val followed by Table.followed referencing Profile
    val follower by Table.follower referencing Profile

    companion object : LongEntityClass<Follow>(Table)
    object Table : LongIdTable() {
        val followed = reference(name = "followed_profile_id", Profile.Table, onDelete = ReferenceOption.CASCADE)
        val follower = reference(name = "following_profile_id", Profile.Table, onDelete = ReferenceOption.CASCADE)
        val isApproved = bool(name = "is_approved")

        init {
            uniqueIndex(follower, followed)
        }
    }
}
