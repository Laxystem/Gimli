/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.server.impl

import quest.laxla.gimli.Element


internal interface ElementProvider<T, in Dao> : Element.Provider<T> where T : Element<T> {
    fun Dao.convert(): T
}
