/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.ld

import com.apicatalog.jsonld.http.DefaultHttpClient
import com.apicatalog.jsonld.loader.HttpLoader

const val OutputContentType = "application/ld+json;profile=\"https://www.w3.org/ns/activitystreams\""
const val AcceptedContentTypes = "$OutputContentType,application/activity+json;q=0.9"

internal val loader = HttpLoader { targetUri, _ ->
    DefaultHttpClient.defaultInstance().send(targetUri, AcceptedContentTypes)
}

