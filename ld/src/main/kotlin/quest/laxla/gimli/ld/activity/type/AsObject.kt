package quest.laxla.gimli.ld.activity.type

import kotlinx.serialization.SerialName
import quest.laxla.gimli.ld.activity.ActivityStreams
import quest.laxla.gimli.ld.activity.Type
import quest.laxla.gimli.ld.activity.property.AspID
import quest.laxla.gimli.ld.activity.property.AspType
import quest.laxla.gimli.ld.activity.toActivityStreamsType

@SerialName(ActivityStreams + "Object")
interface AsObject : AspID, AspType {
    companion object : Type by "Object".toActivityStreamsType()
}
