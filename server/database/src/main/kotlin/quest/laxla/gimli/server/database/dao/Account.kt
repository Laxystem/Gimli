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


class Account(id: EntityID<Long>) : LongEntity(id) {
    val voter by Table.voter referencing Voter
    val creationTime by Table.creationTime

    companion object : LongEntityClass<Account>(Table)
    object Table : LongIdTable() {
        val voter = reference(name = "voter_id", Voter.Table, ReferenceOption.RESTRICT).uniqueIndex()
        val creationTime = creationTime()
    }
}
