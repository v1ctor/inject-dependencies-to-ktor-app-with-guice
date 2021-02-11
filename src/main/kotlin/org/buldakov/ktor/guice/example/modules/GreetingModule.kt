package org.buldakov.ktor.guice.example.modules

import com.google.inject.AbstractModule
import org.buldakov.ktor.guice.example.providers.GreetingProvider
import org.buldakov.ktor.guice.example.providers.IGreetingProvider

class GreetingModule : AbstractModule() {

    override fun configure() {
        bind(IGreetingProvider::class.java).to(GreetingProvider::class.java).asEagerSingleton()
    }
}