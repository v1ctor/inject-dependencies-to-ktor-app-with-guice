package org.buldakov.ktor.guice.example.modules

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.AbstractModule
import com.google.inject.Provides


class JsonModule : AbstractModule() {

    @Provides
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .registerKotlinModule()
            .enable(SerializationFeature.INDENT_OUTPUT)
    }
}
