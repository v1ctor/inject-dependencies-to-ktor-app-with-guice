package org.buldakov.ktor.guice.example.modules

import com.google.inject.AbstractModule
import org.buldakov.ktor.guice.example.providers.GreetingProvider

class GreetingModule : AbstractModule() {

    override fun configure() {
        bind(GreetingProvider::class.java).asEagerSingleton()
    }
}