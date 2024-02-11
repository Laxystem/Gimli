/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.server.impl

import quest.laxla.gimli.Authorizable
import quest.laxla.gimli.Element
import quest.laxla.gimli.server.database.dao.Authorizable as AuthorizableDao

object AuthorizableProvider : ElementProvider<Authorizable, AuthorizableDao> {

    private data class Impl(override val idAtLocalInstance: Long) : Authorizable {
        override val provider: Element.Provider<Authorizable>
            get() = AuthorizableProvider
    }

    override fun AuthorizableDao.convert(): Authorizable = Impl(id.value)

    override fun get(idAtHomeInstance: Long): Authorizable = AuthorizableDao[idAtHomeInstance].convert()
}
