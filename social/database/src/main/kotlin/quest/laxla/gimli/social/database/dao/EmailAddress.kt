/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.social.database.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import quest.laxla.gimli.social.database.domain
import quest.laxla.gimli.social.database.referencing

class EmailAddress(id: EntityID<Long>) : LongEntity(id) {
    val account by Table.account referencing Account
    val localPart by Table.localPart
    val domain by Table.domain
    val isBackup by Table.isBackup
    val isVerified by Table.isVerified

    companion object : LongEntityClass<EmailAddress>(Table)
    object Table : LongIdTable() {
        val account = reference(name = "account_id", Account.Table, onDelete = ReferenceOption.CASCADE)
        val localPart = varchar(name = "local_part", length = 64)
        val domain = domain()
        val isBackup = bool(name = "is_backup").default(false)
        val isVerified = bool(name = "is_verified").default(false)

        init {
            uniqueIndex(localPart, domain)
        }
    }
}
