package org.buldakov.ktor.guice.example.modules

import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Provides
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class EngineModule : AbstractModule() {

    override fun configure() {
        install(ApplicationModule())
    }

    @Provides
    @Inject
    fun engine(environment: ApplicationEngineEnvironment): ApplicationEngine {
        return embeddedServer(Netty, environment)
    }
}