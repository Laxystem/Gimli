package quest.laxla.gimli.server.impl

import com.eygraber.uri.Builder
import kotlinx.collections.immutable.persistentListOf
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.util.Uri
import quest.laxla.gimli.util.*

/**
 * Manages identifiers for a fediverse instance.
 *
 * @since 0.0.1-alpha.3
 * @see IdentifierManager.Simple
 */
interface IdentifierManager {
    /**
     * The hostname this instance uses to generate identifiers.
     *
     * @since 0.0.1-alpha.3
     * @see builder
     */
    val primaryHost: String

    /**
     * Creates a URI [Builder] with the given [scheme].
     *
     * Note executing the [Builder.scheme] function on the returned [Builder] may not equal
     * to using the [scheme] parameter.
     *
     * @since 0.0.1-alpha.3
     * @see build
     */
    fun builder(scheme: String? = Https): Builder

    /**
     * Is this [uri] is considered locally-owned?
     *
     * Generally, returns true if the [hostname][com.eygraber.uri.Uri.host] is equal to [primaryHost],
     * [`localhost`][localhost], or to a secondary host name.
     *
     * @since 0.0.1-alpha.3
     */
    fun isLocal(uri: Uri): Boolean

    /**
     * Formats [uri], and verifies it can be used safely.
     *
     * If [uri] isn't [absolute][com.eygraber.uri.Uri.isAbsolute],
     * or doesn't conform to any other limitation the implementation may impose,
     * this function will return null.
     *
     * If [local] isn't `null`:
     * * It must be equal to [isLocal].
     * * If [hostname][com.eygraber.uri.Uri.host] equals to [`localhost`][localhost],
     * it'll be replaced with the [primaryHost] if [isLocal] is `true`; Otherwise, `null` will be returned.
     * * Implementations may impose additional limitations.
     */
    fun standardize(uri: Uri, local: Boolean? = null): Uri?

    /**
     * Creates a [Uri] from a [String].
     *
     * The resulting URI is guaranteed to be allowed by [standardize];
     * Furthermore, it is guaranteed to be [equal][equals] to [standardize]'s result if passed to it.
     *
     * @see standardize
     * @see Uri.pars
     */
    fun createURI(uri: String, local: Boolean? = null): Uri? = Uri.parseOrNull(uri)?.let(::standardize)

    /**
     * The simplest [IdentifierManager] possible.
     *
     * @since 0.0.1-alpha.3
     */
    data class Simple(
        override val primaryHost: String,
        private val secondaryHosts: ImmutableList<String>
    ) : IdentifierManager {
        constructor(primaryHost: String, vararg secondaryHosts: String) : this(
            primaryHost, persistentListOf(*secondaryHosts)
        )

        override fun builder(scheme: String?): Builder = Uri.EMPTY.buildUpon().authority(primaryHost).scheme(scheme)

        override fun isLocal(uri: Uri) =
            uri.host != null && (uri.host == primaryHost || uri.host == localhost || uri.host in secondaryHosts)

        override fun standardize(uri: Uri, local: Boolean?): Uri? =
            uri.takeIf(Uri::isAbsolute)?.let(Uri::buildUpon)?.apply {
                if (uri.host == localhost) when (local) {
                    true -> setAuthority(primaryHost, uri.port, uri.userInfo)
                    false -> return null
                    null -> {}
                }
            }?.build()
    }

    companion object {
        /**
         * @since 0.0.1-alpha.3
         */
        const val Https = "https"

        /**
         * @since 0.0.1-alpha.3
         */
        const val Acct = "acct"

        /**
         * @since 0.0.1-alpha.3
         */
        const val localhost = "localhost"

        /**
         * Creates a [Uri] with the given [scheme].
         *
         * Note executing the [Builder.scheme] function within [block] on the returned [Builder] may not equal
         * to using the [scheme] parameter.
         *
         * @since 0.0.1-alpha.3
         */
        inline fun IdentifierManager.build(scheme: String? = Https, block: Builder.() -> Unit) =
            builder(scheme).apply(block).build()
    }
}
