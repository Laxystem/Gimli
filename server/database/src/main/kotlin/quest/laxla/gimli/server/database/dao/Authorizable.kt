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
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.server.database.*

class Authorizable(id: EntityID<Long>) : LongEntity(id) {
    val identifier by Table.identifier referencing FederalIdentifier

    companion object : LongEntityClass<Authorizable>(Table)
    object Identifier : FederalIdentification.Companion<Authorizable>(Authorizable)
    object Table : LongIdTable() {
        val identifier = reference(name = "identifier_id", FederalIdentifier.Table, onDelete = ReferenceOption.RESTRICT)
    }
}
