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
import quest.laxla.gimli.social.database.FederalEntity
import quest.laxla.gimli.social.database.referencing

class Railway(id: EntityID<Long>) : FederalEntity(id, Table) {
    val guild by Table.guild referencing Guild

    companion object : LongEntityClass<Railway>(Table)
    object Table : FederalEntity.Table() {
        val guild = reference(name = "guild_id", Guild.Table, onDelete = ReferenceOption.CASCADE)
    }
}
