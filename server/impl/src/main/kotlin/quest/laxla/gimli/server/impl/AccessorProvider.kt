/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.server.impl

import quest.laxla.gimli.Accessor
import quest.laxla.gimli.Element
import quest.laxla.gimli.server.database.dao.Accessor as AccessorDao

object AccessorProvider : ElementProvider<Accessor, AccessorDao> {

    private data class Impl(override val idAtLocalInstance: Long) : Accessor {
        override val provider: Element.Provider<Accessor>
            get() = AccessorProvider
    }

    override fun AccessorDao.convert(): Accessor = Impl(id.value)

    override fun get(idAtHomeInstance: Long): Accessor = AccessorDao[idAtHomeInstance].convert()
}