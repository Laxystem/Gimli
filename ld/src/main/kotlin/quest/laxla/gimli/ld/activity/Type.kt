package quest.laxla.gimli.ld.activity

import quest.laxla.gimli.ld.Iri

interface Type {
    val name: Iri

    companion object {
        operator fun invoke(name: Iri) = object : Type {
            override val name: Iri get() = name
        }
    }
}

fun String.toType() = Type(name = this)
fun String.toActivityStreamsType() = Type(name = ActivityStreams + this)
