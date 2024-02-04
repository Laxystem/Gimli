package quest.laxla.gimli

/**
 * Signifies implementations of this property must be `lateinit`.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class MustBeLateInit
