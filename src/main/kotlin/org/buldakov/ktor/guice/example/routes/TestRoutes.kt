package org.buldakov.ktor.guice.example.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.buldakov.ktor.guice.example.providers.GreetingProvider
import javax.inject.Inject

class TestRoutes @Inject constructor(private val greetingProvider: GreetingProvider) : Routes {

    override fun config(): Route.() -> Unit = fun Route.() {
        get("status") {
            call.respond(mapOf("status" to "OK"))
        }
        get("greet") {
            call.respond(mapOf("message" to greetingProvider.getGreeting()))
        }
    }

}