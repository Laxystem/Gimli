package quest.laxla.gimli.util

// region --- Regex Groups ---

const val domain = "domain"
const val hostname = "hostname"
const val ipv4 = "ipv4"
const val ipv6 = "ipv6"
const val profileMention = "profileMention"
const val matrixProfileMention = "matrixProfileMention"
const val gimliProfileMention = "gimliProfileMention"
const val port = "port"
const val socketAddress = "socketAddress"
const val username = "username"

// endregion
// region --- Utilities ---
// region Domain

const val ShortestDomain = "x.x"
val DomainLength = ShortestDomain.length.toUInt()..255u
val DomainRegex =
    "(?<$domain>(?=\\S{${DomainLength.first},${DomainLength.last}})(?:[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]{0,4}[a-z0-9])?)".toRegex()

// endregion
// region IPv4

val IPv4Length = 1u..15u // by spec
val IPv4Regex = // source: https://stackoverflow.com/a/36760050
    "(?<$ipv4>(?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]))".toRegex()

// endregion
// region IPv6

val IPv6Length = 2u..45u // by spec
val IPv6Regex = // source: https://stackoverflow.com/a/17871737
    "(?<$ipv6>(?:[0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|(?:[0-9a-fA-F]{1,4}:){1,7}:|(?:[0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|(?:[0-9a-fA-F]{1,4}:){1,5}(?::[0-9a-fA-F]{1,4}){1,2}|(?:[0-9a-fA-F]{1,4}:){1,4}(?::[0-9a-fA-F]{1,4}){1,3}|(?:[0-9a-fA-F]{1,4}:){1,3}(?::[0-9a-fA-F]{1,4}){1,4}|(?:[0-9a-fA-F]{1,4}:){1,2}(?::[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:(?:(?::[0-9a-fA-F]{1,4}){1,6})|:(?:(?::[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(?::[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(?:ffff(?::0{1,4}){0,1}:){0,1}(?:(?:25[0-5]|(?:2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(?:25[0-5]|(?:2[0-4]|1{0,1}[0-9]){0,1}[0-9])|(?:[0-9a-fA-F]{1,4}:){1,4}:(?:(?:25[0-5]|(?:2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(?:25[0-5]|(?:2[0-4]|1{0,1}[0-9]){0,1}[0-9]))".toRegex()

// endregion
// region Hostname

val HostnameLength = minOf(DomainLength.first, IPv4Length.first, IPv4Length.first)..maxOf(
    DomainLength.last,
    IPv4Length.last,
    IPv6Length.last
)
val HostnameRegex = "(?<$hostname>$DomainRegex|$IPv4Regex|$IPv6Regex|\\[$IPv6Regex])".toRegex()

//endregion
//region Port

val Ports = 1u..65535u
val PortLength = Ports.first.toString().length.toUInt()..Ports.last.toString().length.toUInt()
val PortRegex = // https://stackoverflow.com/a/12968117
    "(?<$port>[1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])".toRegex()

// endregion
// region Socket Address

val SocketAddressLength = (HostnameLength.first + PortLength.first)..(HostnameLength.last + PortLength.last)
val SocketAddressRegex = "(?<$socketAddress>$HostnameRegex(?::$PortRegex)?)".toRegex()

// endregion
// endregion
// region --- Gimli ---

const val GimliSeparator = "/"
const val GimliProfileMentionPrefix = "@"

// region Gimli Username

val GimliUsernameLength = 3u..32u // by spec

val GimliUsernameRegex =
    "(?<$username>[a-z][a-z0-9._-]{${GimliUsernameLength.first - 2u},${GimliUsernameLength.last - 2u}}[a-z0-9])".toRegex()

// endregion
// region Gimli Profile Mention

val GimliProfileMentionLength =
    GimliProfileMentionPrefix.length.toUInt() + GimliUsernameLength.first + GimliSeparator.length.toUInt() + DomainLength.last

val GimliProfileMentionRegex =
    "(?<$gimliProfileMention>$GimliProfileMentionPrefix$DomainRegex$GimliSeparator$GimliUsernameRegex)".toRegex()

// endregion
// endregion
// region --- Matrix ---

const val MatrixSeparator = ":"
const val MatrixProfileMentionPrefix = "@"
private const val MatrixMaxProfileMentionLength = 255u // by spec

// region Matrix Username

val MatrixUsernameLength = 1u..(0u // by spec
        + MatrixMaxProfileMentionLength
        - MatrixProfileMentionPrefix.length.toUInt()
        - MatrixSeparator.length.toUInt()
        - HostnameLength.first)

val MatrixUsernameRegex =
    "(?<$username>[a-z0-9._=/+-]{${MatrixUsernameLength.first},${MatrixUsernameLength.last}})".toRegex()

// endregion
// region Matrix Profile Mention

val MatrixProfileMentionLength = (0u
        + MatrixProfileMentionPrefix.length.toUInt()
        + MatrixUsernameLength.first
        + MatrixSeparator.length.toUInt()
        + HostnameLength.first)..MatrixMaxProfileMentionLength

val MatrixProfileMentionRegex =
    "(?<$matrixProfileMention>$MatrixProfileMentionPrefix$MatrixUsernameRegex$MatrixSeparator$SocketAddressRegex)".toRegex()

// endregion
// endregion