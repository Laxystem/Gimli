package quest.laxla.gimli

import kotlinx.serialization.Serializable

sealed interface Account {
    interface Actual : Element<Actual>, Account

    @Serializable
    data class UpdateBuilder(override val idAtHomeInstance: Long) : Element.Builder.Update<UpdateBuilder>, Account {
        override fun clone(): UpdateBuilder = copy()
    }

    @Serializable
    data object CreateBuilder : Element.Builder.Create<CreateBuilder>, Account {
        override fun clone(): CreateBuilder = this
    }
}
