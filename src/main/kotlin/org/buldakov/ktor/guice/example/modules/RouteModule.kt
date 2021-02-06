package org.buldakov.ktor.guice.example.modules

import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import org.buldakov.ktor.guice.example.routes.Routes
import org.buldakov.ktor.guice.example.routes.TestRoutes

class RouteModule : AbstractModule() {

    override fun configure() {
        val routeBinder = Multibinder.newSetBinder(binder(), Routes::class.java)
        routeBinder.addBinding().to(TestRoutes::class.java)
    }
}