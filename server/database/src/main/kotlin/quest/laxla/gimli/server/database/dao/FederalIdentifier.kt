/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.server.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

class FederalIdentifier(id: EntityID<Long>): LongEntity(id) {
    val value by Table.value

    companion object : LongEntityClass<FederalIdentifier>(Table)
    object Table : LongIdTable() {
        val value = varchar(name = "value", length = 4096).uniqueIndex()
    }
}
