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
import quest.laxla.gimli.social.database.FederalEntity

class Voter(id: EntityID<Long>) : FederalEntity(id, Table) {

    companion object : LongEntityClass<Voter>(Table)
    object Table : FederalEntity.Table()
}
