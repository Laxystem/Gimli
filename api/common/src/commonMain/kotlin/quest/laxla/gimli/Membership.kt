/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

/**
 * Represents a follow or membership request.
 */
public enum class Membership {
    /**
     * Membership has been requested and accepted.
     */
    Accepted,

    /**
     * Membership has been requested, but not yet accepted.
     */
    Requested;
}
