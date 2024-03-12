/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

public object Permission {
    /**
     * Assigns variables their name with a prefix based on [Specification.Gimli].
     */
    private operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, String> {
        val name = Specification.Gimli.baseUrl + "permission/" + property.name
        return ReadOnlyProperty { _, _ -> name }
    }

    // TODO: replace example permissions

    /**
     * Change the authorizable [Profile]'s [display name][Profile.displayName], avatar, etc.
     *
     * Change the accessor [Profile]'s nickname, etc. within the authorizable [Guild].
     */
    public val ManageSelf: String by Permission

    /**
     * Bypass [Vote]s targeting the authorizable,
     * forcing them to succeed if [enabled][Topic.isAllowingBypasses].
     */
    public val Bypass: String by Permission

    /**
     * Veto [Vote]s targeting the authorizable to fail if [enabled][Topic.isAllowingVetoes].
     */
    public val Veto: String by Permission

    /**
     * Edit or delete a [Post] sent by the authorizable [Profile],
     * whose sending action was created by the accessor.
     *
     * Revert the latest edits or edit a [Post] sent by the authorizable [Profile],
     * if all reverted edits (if any) and the latest edit were performed by the accessor.
     */
    public val ManageSelfMessages: String by Permission

    /**
     * Edit any message sent by the authorizable [Profile].
     */
    public val EditMessages: String by Permission

    /**
     * Delete any message sent by the authorizable [Profile].
     *
     * Remove the [railway][Post.railway] from any message sent to the authorizable [Railway],
     * or to a [Railway] belonging to the authorizable [Guild].
     *
     * Remove [Tag]s claimed by the authorizable [Guild] from any message.
     */
    public val DeleteMessages: String by Permission

    /**
     * Mention the authorizable
     */
    public val Mention: String by Permission
}
