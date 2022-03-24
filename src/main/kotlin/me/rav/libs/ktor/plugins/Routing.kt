package me.rav.libs.ktor.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        route("/main") {
            get {
                call.respond("This is main page")
            }
            get("/login") {
                call.respond("Please, login!")
            }
        }

        route ("/resources") {
            val users = mapOf(1 to "John", 2 to "Kate", 3 to "Mike")
            get("/{id}") {
                call.parameters["id"]?.let { stringId ->
                    stringId.toIntOrNull()?.let { id ->
                        users[id]?.let { user ->
                            call.respondText(user)
                        }
                    }
                }
            }

            get {
                call.request.queryParameters["id"]?.let { stringId ->
                    stringId.toIntOrNull()?.let { id ->
                        users[id]?.let { user ->
                            call.respondText(user)
                        }
                    }
                }
            }
        }
    }
}
