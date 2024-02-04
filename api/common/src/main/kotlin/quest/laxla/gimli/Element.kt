package quest.laxla.gimli

import kotlinx.coroutines.flow.emptyFlow
import kotlinx.serialization.Transient
import quest.laxla.gimli.util.CatchingFlow
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Gimli API element, modifiable and creatable via [Provider]s.
 */
interface Element<out T> : Identified where T : Element<T> {
    /**
     * The [Provider] that created this element.
     *
     * Mustn't be serialized.
     */
    @Transient
    val provider: Provider<T>

    /**
     * Gimli API Element federalized between different instances.
     */
    interface Federalized<out T> : Element<T> where T : Federalized<T> {
        val externalFederalIdentifiers: CatchingFlow<String> get() = emptyFlow()
    }

    /**
     * [Updates][Updating], [creates][Creating], and [fetches][get] [Element]s.
     */
    interface Provider<out T> where T : Element<T> {
        /**
         * Retrieve an element by its [idAtHomeInstance].
         */
        operator fun get(idAtHomeInstance: Long): T

        /**
         * A [Provider] capable of creating new elements.
         */
        interface Creating<out T, CreateBuilder> : Provider<T>
                where T : Element<T>, CreateBuilder : Builder.Create<CreateBuilder> {

            /**
             * Create an element from [builder].
             */
            fun create(builder: CreateBuilder)

            /**
             * Create an element from [builder], and return its value.
             */
            suspend fun createAndGet(builder: CreateBuilder): T

            companion object {
                /**
                 * Apply [block] to [builder], then create an element from it.
                 */
                @OptIn(ExperimentalContracts::class)
                inline fun <T, CreateBuilder> Creating<T, CreateBuilder>.create(
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
                suspend inline fun <T, CreateBuilder> Creating<T, CreateBuilder>.createAndGet(
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
        interface Updating<out T, UpdateBuilder> : Provider<T>
                where T : Element<T>, UpdateBuilder : Builder.Update<UpdateBuilder> {

            /**
             * Update an element, as described in [builder].
             */
            fun update(builder: UpdateBuilder)

            /**
             * Update an element as described in [builder], and return it.
             */
            suspend fun updateAndGet(builder: UpdateBuilder): T

            companion object {

                /**
                 * Update an element.
                 */
                @OptIn(ExperimentalContracts::class)
                inline fun <T, UpdateBuilder> Updating<T, UpdateBuilder>.update(
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
                suspend inline fun <T, UpdateBuilder> Updating<T, UpdateBuilder>.updateAndGet(
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

        /**
         * A [Provider] capable of fetching elements via their federal Identifier.
         */
        interface Federal<out T> : Provider<T> where T : Federalized<T> {
            operator fun get(federalIdentifier: String): T
        }
    }

    /**
     * [Element] builder.
     *
     * All properties with a default value must be mutable, and vice versa.
     * Mutable collections must be stored as `val`s.
     * Implementations must be [Serializable].
     *
     * @see Update
     * @see Create
     */
    sealed interface Builder<This> : Cloneable where This : Builder<This> {
        override fun clone(): This

        /**
         * [Element] creation builder.
         *
         * @see Builder
         * @see Provider.Creating
         */
        interface Create<This> : Builder<This> where This : Create<This>

        /**
         * [Element] update builder.
         *
         * Mutable properties must be nullable and have a default value of `null`, meaning "unchanged".
         *
         * @see Builder
         * @see Provider.Updating
         */
        interface Update<This> : Builder<This>, Identified where This : Update<This>
    }
}
