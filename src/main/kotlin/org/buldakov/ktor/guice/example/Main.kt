package org.buldakov.ktor.guice.example

import com.google.inject.Guice
import io.ktor.server.engine.*
import org.buldakov.ktor.guice.example.modules.ApplicationModule
import org.buldakov.ktor.guice.example.modules.GreetingModule
import org.buldakov.ktor.guice.example.modules.JsonModule
import org.buldakov.ktor.guice.example.modules.PropertyModule

fun main() {
    val modules = listOf(PropertyModule(),
        ApplicationModule(),
        JsonModule(),
        GreetingModule())

    val injector = Guice.createInjector(modules)
    val engine = injector.getInstance(ApplicationEngine::class.java)

    engine.start(wait = true)
}