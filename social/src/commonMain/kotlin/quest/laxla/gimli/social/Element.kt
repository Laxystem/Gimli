/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.social

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Transient
import quest.laxla.gimli.util.Uri
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Gimli API element, modifiable and creatable via [Provider]s.
 */
public interface Element<T> : Identified where T : Element<T> {
    /**
     * The [Provider] that created this element.
     *
     * Mustn't be serialized.
     */
    @Transient
    public val provider: Provider<T>

    /**
     * [Element] that may be managed at other instances.
     */
    public interface Federalized<T> : Element<T> where T : Federalized<T> {
        public val externalFederalIdentifiers: Flow<String>

        @Transient
        override val provider: Provider<T>
    }

    /**
     * Responsible for [fetching][get], [updating][Updating], and [creating][Creating] [Element]s.
     *
     * All elements [reference][provider] the Provider that created them.
     */
    public interface Provider<T> where T : Element<T> {
        /**
         * Retrieve an element by its [federalIdentifier].
         */
        public suspend fun get(federalIdentifier: Uri, invalidateCache: Boolean = false): T

        public companion object {
            public suspend fun <T> Provider<T>.get(
                ref: Ref<T>,
                invalidateCache: Boolean = false
            ): T where T : Element<T> = get(ref.primaryFederalIdentifier, invalidateCache)
        }

        /**
         * A [Provider] capable of creating new elements.
         */
        public interface Creating<T, CreateBuilder> : Provider<T>
                where T : Element<T>, CreateBuilder : Builder.Create<CreateBuilder> {

            /**
             * Create an element from [builder].
             */
            public fun create(builder: CreateBuilder)

            /**
             * Create an element from [builder], and return its value.
             */
            public suspend fun createAndGet(builder: CreateBuilder): T

            public companion object {
                /**
                 * Apply [block] to [builder], then create an element from it.
                 */
                @OptIn(ExperimentalContracts::class)
                public inline fun <T, CreateBuilder> Creating<T, CreateBuilder>.create(
                    builder: CreateBuilder,
                    block: CreateBuilder.() -> Unit
                ) where T : Element<T>, CreateBuilder : Builder.Create<CreateBuilder> {
                    contract {
                        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
                    }

                    create(builder.apply(block))
                }

                /**
                 * Apply [block] to [builder], then create an element, and return its value.
                 */
                @OptIn(ExperimentalContracts::class)
                public suspend inline fun <T, CreateBuilder> Creating<T, CreateBuilder>.createAndGet(
                    builder: CreateBuilder,
                    block: CreateBuilder.() -> Unit
                ): T where T : Element<T>, CreateBuilder : Builder.Create<CreateBuilder> {
                    contract {
                        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
                    }

                    return createAndGet(builder.apply(block))
                }
            }
        }

        /**
         * A [Provider] capable of updating existing elements.
         */
        public interface Updating<T, UpdateBuilder> : Provider<T>
                where T : Element<T>, UpdateBuilder : Builder.Update<UpdateBuilder> {

            /**
             * Update an element, as described in [builder].
             */
            public fun update(builder: UpdateBuilder)

            /**
             * Update an element as described in [builder], and return it.
             */
            public suspend fun updateAndGet(builder: UpdateBuilder): T

            public companion object {

                /**
                 * Update an element.
                 */
                @OptIn(ExperimentalContracts::class)
                public inline fun <T, UpdateBuilder> Updating<T, UpdateBuilder>.update(
                    builder: UpdateBuilder,
                    block: UpdateBuilder.() -> Unit
                ) where T : Element<T>, UpdateBuilder : Builder.Update<UpdateBuilder> {
                    contract {
                        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
                    }

                    update(builder.apply(block))
                }

                /**
                 * Update an element and return it.
                 */
                @OptIn(ExperimentalContracts::class)
                public suspend inline fun <T, UpdateBuilder> Updating<T, UpdateBuilder>.updateAndGet(
                    builder: UpdateBuilder,
                    block: UpdateBuilder.() -> Unit
                ): T where T : Element<T>, UpdateBuilder : Builder.Update<UpdateBuilder> {
                    contract {
                        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
                    }

                    return updateAndGet(builder.apply(block))
                }
            }
        }
    }

    /**
     * [Element] builder.
     *
     * All properties must be `var`s.
     *
     * Implementations must be [Serializable].
     *
     * @see Update
     * @see Create
     */
    public sealed interface Builder<This> where This : Builder<This> {
        public fun clone(): This

        /**
         * [Element] creation builder.
         *
         * Collections must be `val`s, and [mutable][MutableCollection].
         *
         * TODO: [MutableMap]s support
         *
         * @see Builder
         * @see Provider.Creating
         */
        public interface Create<This> : Builder<This> where This : Create<This>

        /**
         * [Element] update builder.
         *
         * Properties must be mutable and use [Optional][quest.laxla.gimli.util.Optional]s,
         * with a default value of [Optional.Empty][quest.laxla.gimli.util.Optional.Empty]
         *
         * TODO: [MutableMap]s support.
         *
         * @see Builder
         * @see Provider.Updating
         */
        public interface Update<This> : Builder<This>, Identified where This : Update<This>
    }
}
