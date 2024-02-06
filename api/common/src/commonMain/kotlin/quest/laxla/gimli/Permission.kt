package quest.laxla.gimli

import quest.laxla.gimli.util.Maybe

public interface Permission { // TODO: serialization
    public val identifier: String
    public val appliesToGuilds: Maybe
    public val appliesToProfiles: Maybe
    public val appliesToRailways: Maybe

    public enum class BuiltIn( // TODO: replace example permissions
        override val appliesToProfiles: Boolean = true,
        override val appliesToGuilds: Boolean = true,
        override val appliesToRailways: Boolean = true
    ) : Permission {
        /**
         * Change the authorizable [Profile]'s [display name][Profile.displayName], avatar, etc.
         *
         * Change the accessor [Profile]'s nickname, etc. within the authorizable [Guild].
         */
        ManageSelf(appliesToRailways = false),

        /**
         * Bypass [Vote]s targeting the authorizable,
         * forcing them to succeed if [enabled][Authorizable.isAllowingBypasses].
         */
        Bypass,

        /**
         * Veto [Vote]s targeting the authorizable to fail if [enabled][Authorizable.isAllowingVetoes].
         */
        Veto,

        /**
         * Edit or delete a [Message] sent by the authorizable [Profile],
         * whose sending action was created by the accessor.
         *
         * Revert the latest edits or edit a [Message] sent by the authorizable [Profile],
         * if all reverted edits (if any) and the latest edit were performed by the accessor.
         */
        ManageSelfMessages(appliesToRailways = false, appliesToGuilds = false),

        /**
         * Edit any message sent by the authorizable [Profile].
         */
        EditMessages(appliesToRailways = false, appliesToGuilds = false),

        /**
         * Delete any message sent by the authorizable [Profile].
         *
         * Remove the [railway][Message.railway] from any message sent to the authorizable [Railway],
         * or to a [Railway] belonging to the authorizable [Guild].
         *
         * Remove [Tag]s claimed by the authorizable [Guild] from any message.
         */
        DeleteMessages,

        /**
         * Mention the authorizable
         */
        Mention;

        override val identifier: String get() = "https://gimli.laxla.quest#PRE_ALPHA/permission/$name"
    }
}
