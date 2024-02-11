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
import quest.laxla.gimli.server.database.FederalIdentification

class Accessor(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Accessor>(Table)
    class Identifier : FederalIdentification.Companion<Accessor>(Accessor)
    object Table : LongIdTable()
}
