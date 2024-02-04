package quest.laxla.gimli

/**
 * Signifies a property is required for federation.
 *
 * @property fallback details what will Gimli fall back to if this property isn't provided.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.PROPERTY_SETTER)
annotation class Federated(val fallback: Fallback = Fallback.Default) {
    enum class Fallback {
        /**
         * The property will fall back to its default value.
         *
         * Requires the property to have a default getter.
         */
        Default,

        /**
         * Gimli will defederate completely with the violating instance.
         */
        Defederation,

        /**
         * Gimli will consider the violating instance as incompatible with this feature.
         */
        Incompatibility,

        Other,

        /**
         * Gimli clients and/or servers may perform *any* action when encountering a missing property.
         */
        Undefined
    }
}
