package me.rav.libs.ktor

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.rav.libs.ktor.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
//        install(HttpsRedirect)

        configureRouting()
    }.start(wait = true)
}
