package quest.laxla.gimli

import quest.laxla.gimli.Ref.*

/**
 * References an [element][Element] [directly][Direct],
 * [via numeral identifier][Numeral],
 * or [via federal identifier][Federal].
 */
public sealed interface Ref<T> : Identified where T : Element<T> { // TODO: serialize
    public data class Direct<T>(val value: T) : Ref<T> where T : Element<T> {
        override val primaryFederalIdentifier: String get() = value.primaryFederalIdentifier
    }

    public data class Federal<T>(val identifier: String) : Ref<T> where T : Element.Federalized<T> {
        override val primaryFederalIdentifier: String get() = identifier
    }

    public companion object {
        public val <T> T.ref: Direct<T> where T : Element<T> get() = Direct(value = this)
    }
}
