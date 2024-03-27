/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.server.impl

import quest.laxla.gimli.Element
import quest.laxla.gimli.Voter
import quest.laxla.gimli.server.impl.IdentifierManager.Companion.build
import quest.laxla.gimli.util.Uri
import quest.laxla.gimli.server.database.dao.Voter as VoterDao

class VoterProvider(override val instance: GimliInstance) : LocalProvider<Voter, VoterDao> {
    private inner class Impl(dao: VoterDao) : Voter {
        override val primaryFederalIdentifier: Uri =
            dao.externalIdentifier ?: instance.identifierManager.build {
                appendPath("auth")
                appendPath("voter")
                appendPath(dao.id.value.toString())
            }

        override val provider: Element.Provider<Voter>
            get() = this@VoterProvider
    }

    override fun convert(voter: VoterDao): Voter = Impl(voter)

    override suspend fun get(federalIdentifier: Uri, invalidateCache: Boolean): Voter =
        get(federalIdentifier, VoterDao) {
            federalIdentifier.pathSegments[2].toLong()
        }
}
