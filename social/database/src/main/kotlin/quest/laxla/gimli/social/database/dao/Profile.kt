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
import quest.laxla.gimli.social.database.creationTime
import quest.laxla.gimli.social.database.referencing

class Profile(id: EntityID<Long>) : FederalEntity(id, Table) {
    val about by Table.about
    val displayName by Table.displayName
    val voter by Table.voter referencing Voter
    val topic by Table.topic referencing Topic
    val creationTime by Table.creationTime

    companion object : LongEntityClass<Profile>(Table)
    object Table : FederalEntity.Table() {
        val about = text(name = "about")
        val voter = reference(name = "voter_id", Voter.Table, ReferenceOption.RESTRICT).uniqueIndex()
        val topic = reference(name = "topic_id", Topic.Table, ReferenceOption.RESTRICT).uniqueIndex()
        val creationTime = creationTime()
        val displayName = varchar(name = "display_name", length = 32)
    }
}
