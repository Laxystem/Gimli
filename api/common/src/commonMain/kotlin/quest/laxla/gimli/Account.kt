package quest.laxla.gimli

import kotlinx.serialization.Serializable

public sealed interface Account {
    public interface Actual : Element<Actual>, Account

    @Serializable
    public data object CreateBuilder : Element.Builder.Create<CreateBuilder>, Account {
        override fun clone(): CreateBuilder = this
    }

    @Serializable
    public data class UpdateBuilder(override val idAtHomeInstance: Long) : Element.Builder.Update<UpdateBuilder>, Account {
        override fun clone(): UpdateBuilder = copy()
    }
}
