/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.server.impl

/*object ProfileProvider : ElementProvider<Profile.Actual, ProfileDao>, Element.Provider.Federal<Profile.Actual> {
    private data class Impl(
        override val idAtHomeInstance: Long,
        override val accessor: Accessor,
        override val authorizable: Authorizable,
        override val displayName: String,
        override val title: String,
        override val about: String
    ) : Profile.Actual {
        override val provider: Element.Provider<Profile.Actual>
            get() = ProfileProvider
    }

    override fun ProfileDao.convert(): Profile.Actual =
        Impl(id.value, accessor.convert(), authorizable.convert(), displayName, title, about)

    override fun get(idAtHomeInstance: Long): Profile.Actual =
        ProfileDao[idAtHomeInstance].convert()

    override fun get(federalIdentifier: String): Profile.Actual {
        TODO("Not yet implemented")
    }
}*/
