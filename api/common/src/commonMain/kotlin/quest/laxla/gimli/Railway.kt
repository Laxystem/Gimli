package quest.laxla.gimli

import quest.laxla.gimli.util.Maybe
import quest.laxla.gimli.util.emptyString

public interface Railway {
    public val description: String?
    public val displayName: String?
    public val startFromTop: Maybe

    public interface Actual : Railway, Element.Federalized<Actual> {
        override val description: String
        override val displayName: String
        override val startFromTop: Maybe
        public val guild: Guild
    }

    public data class CreateBuilder(
        val guild: Guild,
        override var description: String = emptyString(),
        override var displayName: String = emptyString(),
        override var startFromTop: Boolean = false
    ) : Railway, Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    public data class UpdateBuilder(
        override val idAtHomeInstance: Long,
        override var description: String? = null,
        override var displayName: String? = null,
        override var startFromTop: Boolean? = null
    ) : Railway, Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
