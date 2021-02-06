package org.buldakov.ktor.guice.example.routes

import io.ktor.routing.*

interface Routes {

    fun config(): Route.() -> Unit
}