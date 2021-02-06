package org.buldakov.ktor.guice.example.modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import java.util.*

class PropertyModule : AbstractModule() {
    override fun configure() {

        val ps = Properties()
        ps.load(ClassLoader.getSystemClassLoader().getResourceAsStream("app.properties"))

        binder().requireExactBindingAnnotations()
        Names.bindProperties(binder(), ps)
    }
}