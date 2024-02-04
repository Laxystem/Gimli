package quest.laxla.gimli.server.networking

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import quest.laxla.gimli.server.networking.webfinger.webfinger

fun main() {
    embeddedServer(Netty, port = 8080) {
        webfinger()
    }.start(wait = true)
}
