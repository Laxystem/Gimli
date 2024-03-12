/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package quest.laxla.gimli

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.util.IetfBcp47
import quest.laxla.gimli.util.PersistentList
import quest.laxla.gimli.util.emptyString

@Specification.Compliance(Specification.ActivityPub, name = "Note")
public interface Post : Element<Post> {
    @Specification.Compliance(Specification.ActivityPub, name = "attributedTo")
    public val author: Ref<Profile>

    public val railway: Ref<Railway>?

    @Specification.Compliance(Specification.ActivityPub, name = "inReplyTo")
    public val replyTo: Ref<Post>?

    @Specification.Compliance(Specification.ActivityPub, name = "tag")
    public val tags: ImmutableList<Ref<Tag>>

    @Specification.Compliance(Specification.ActivityPub, name = "to")
    public val explicitMentions: ImmutableList<Ref<Profile>>

    @Specification.Compliance(Specification.ActivityPub, name = "mediaType")
    public val contentMediaType: String

    public val replies: Flow<Post>
    public val replyCount: Int

    public val knownRevisions: ImmutableList<Revision>
    public val revisions: Flow<Revision>

    public fun addTo(tag: String, voter: Voter)
    public fun removeFrom(tag: String, voter: Voter)

    public suspend fun addToAndGet(tag: String, voter: Voter): Post
    public suspend fun removeFromAndGet(tag: String, voter: Voter): Post

    public fun removeRailway()

    public suspend fun removeRailwayAndGet(): Post

    @Serializable
    public data class CreateBuilder(
        val author: Ref<Profile>,
        val replyTo: Post? = null,
        val railway: Railway? = null,
        val title: String = emptyString(),
        val summary: String = emptyString(),
        val body: String = emptyString(),
        val tags: PersistentList<Ref<Tag>> = persistentListOf(),
        val explicitMentions: PersistentList<Ref<Profile>> = persistentListOf()
    ) : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    public interface Revision {
        public val post: Post
        public val language: IetfBcp47

        @Specification.Compliance(Specification.ActivityPub, name = "name")
        public val title: String

        @Specification.Compliance(Specification.ActivityPub, name = "summary")
        public val summary: String

        @Specification.Compliance(Specification.ActivityPub, name = "contents")
        public val contents: String

        @Serializable
        public data class CreateBuilder(
            val post: Post,
            val language: IetfBcp47,
            val title: String = emptyString(),
            val summary: String = emptyString(),
            val contents: String = emptyString()
        ) : Element.Builder.Create<CreateBuilder> {
            override fun clone(): CreateBuilder = copy()
        }
    }
}
