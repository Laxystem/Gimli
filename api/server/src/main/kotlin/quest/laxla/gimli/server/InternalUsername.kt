package quest.laxla.gimli.server

import kotlinx.datetime.Instant
import quest.laxla.gimli.Identified
import quest.laxla.gimli.Profile

interface InternalUsername : Identified {
    val value: String
    val claimTime: Instant
    val profile: Profile
}
