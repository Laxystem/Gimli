/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli

import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.util.Optional

@ClientToServerOnly
public interface Account : Element<Account> {
    /**
     * This [Account]'s [Accessor].
     *
     * If used directly, other accessors authorized to the same [abilities][Ability] will be able
     * to view this account's [primary][EmailAddress.isPrimary] [EmailAddress].
     */
    public val accessor: Accessor

    /**
     * All known [EmailAddress]es belonging to this [Account].
     *
     * Clients may only have access to a partial list of an account's email addresses.
     * If a client is given access to more than one email address,
     * at least one of them has to be the account's [primary][EmailAddress.isPrimary] email address.
     */
    public val emailAddresses: ImmutableList<EmailAddress>

    @Serializable
    public data class CreateBuilder( // TODO: support more ways to sign-up
        val emailAddress: String
    ) : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    @ClientToServerOnly
    public interface EmailAddress : Element<EmailAddress> {

        /**
         * The [Account] this email address belongs to.
         */
        public val account: Account

        /**
         * The email address.
         */
        public val value: String

        /**
         * Is this email address verified?
         */
        public val isVerified: Boolean

        /**
         * Is this [account]'s primary email address?
         *
         * Must be [verified][isVerified].
         * An [Account] may only have a single primary email address.
         */
        public val isPrimary: Boolean

        /**
         * Is this email address public?
         *
         * Only authenticated users will be able to see it.
         * However, other servers may present it to unauthenticated users.
         *
         * Will only have an effect if [verified][isVerified].
         */
        public val isPublic: Boolean

        /**
         * Can this mail address be used to discover users via webfinger?
         *
         * Will only have an effect if [verified][isVerified].
         */
        public val isDiscoverable: Boolean

        @Serializable
        public data class CreateBuilder(
            val emailAddress: String,
            val isPublic: Boolean = false,
            val isDiscoverable: Boolean = false
        ) : Element.Builder.Create<CreateBuilder> {
            override fun clone(): CreateBuilder = copy()
        }

        @Serializable
        public data class UpdateBuilder(
            /**
             * The email address to be updated, using the `mailto` protocol.
             */
            override val primaryFederalIdentifier: String,
            var isPublic: Optional<Boolean> = Optional.Empty,
            var isDiscoverable: Optional<Boolean> = Optional.Empty,
            var isPrimary: Optional<Boolean> = Optional.Empty
        ) : Element.Builder.Update<UpdateBuilder> {
            override fun clone(): UpdateBuilder = copy()
        }
    }

    public companion object {
        /**
         * This [Account]'s primary [EmailAddress], or alternatively, the only known email address, if any.
         *
         * An account must have exactly one primary email address.
         * If a client is given access to more than one email address,
         * it must be given access to the primary email address.
         */
        public val Account.primaryEmailAddress: EmailAddress?
            get() = emailAddresses.singleOrNull() ?: emailAddresses.find(EmailAddress::isPrimary)
    }
}
