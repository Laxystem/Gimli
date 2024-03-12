/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.server.impl

import quest.laxla.gimli.*
import quest.laxla.gimli.server.impl.IdentifierManager.Companion.build
import quest.laxla.gimli.util.Uri
import quest.laxla.gimli.util.substringAfter
import quest.laxla.gimli.util.substringBefore
import quest.laxla.gimli.server.database.dao.Profile as ProfileDao
import quest.laxla.gimli.server.database.dao.Topic as TopicDao
import quest.laxla.gimli.server.database.dao.Voter as VoterDao


class ProfileProvider(override val instance: GimliInstance) : LocalProvider<Profile, ProfileDao>,
    Element.Provider<Profile> {
    private inner class Impl(
        dao: ProfileDao
    ) : Profile {
        override val primaryFederalIdentifier: Uri =
            dao.externalIdentifier ?: instance.identifierManager.build {
                appendPath("profile")
                appendPath(dao.id.toString())
            }
        override val voter: Voter by lazy { instance.get<Voter, VoterDao>().convert(dao.voter) }
        override val topic: Topic by lazy { instance.get<Topic, TopicDao>().convert(dao.topic) }
        override val displayName: String = dao.displayName
        override val about: String = dao.about
        override val preferredUsername: String = TODO()

        override val provider: Element.Provider<Profile>
            get() = this@ProfileProvider
    }

    override fun convert(voter: ProfileDao): Profile = Impl(voter)

    override suspend fun get(
        federalIdentifier: Uri, invalidateCache: Boolean
    ): Profile = if (instance.identifierManager.isLocal(federalIdentifier)) when (federalIdentifier.scheme) {
        IdentifierManager.Acct -> {
            val username = federalIdentifier.userInfo ?: error("Acct URIs must have userinfo.")
            val instance = federalIdentifier.host ?: error("Acct URIs must have a host.")

            get(username, instance, invalidateCache)
        }

        IdentifierManager.Https -> run {
            val usernameSegment = federalIdentifier.pathSegments.getOrNull(0)
                ?: throw IllegalArgumentException("HTTPS profile identifiers must have a path; '$federalIdentifier' doesn't.")
            val username = when {
                usernameSegment == "profile" -> federalIdentifier.pathSegments.getOrNull(1)
                    ?: throw IllegalArgumentException("HTTPS profile identifiers must include a username or an ID; '$federalIdentifier' doesn't.")

                else -> usernameSegment.removePrefix("@")
            }

            if (username == usernameSegment)
                throw IllegalArgumentException(
                    "HTTPS profile identifiers' paths must start with '/profile/' or '/@';" +
                            "The username 'profile' is forbidden; '$federalIdentifier' violates at least one of those requirements."
                )

            return@run if ('@' in username) get( // example.social/@username@example.social, example.social/profile/username@example.social
                username = username.substringBefore('@'),
                instance = username.substringAfter('@'),
                invalidateCache = invalidateCache
            ) else {
                val id = username.toULongOrNull()?.toLong()
                    ?: return@run get(
                        username,
                        invalidateCache = invalidateCache
                    ) // example.social/@username, example.social/profile/username

                get(id, invalidateCache) // example.social/@42, example.social/profile/42 (by ID)
            }
        }

        else -> throw IllegalArgumentException("Profiles identifiers may only be HTTPS and ACCT URIs.")
    } else TODO()

    suspend fun get(username: String, instance: String? = null, invalidateCache: Boolean = false): Profile = TODO()
    suspend fun get(id: Long, invalidateCache: Boolean = false): Profile =
        convert(instance.suspendTransaction { ProfileDao.findById(id) }!!)
}




