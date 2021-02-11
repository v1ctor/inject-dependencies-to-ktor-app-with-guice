package org.buldakov.ktor.guice.example.modules

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.AbstractModule
import com.google.inject.Provides
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.buldakov.ktor.guice.example.routes.Routes
import org.slf4j.event.Level
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class ApplicationModule : AbstractModule() {
    override fun configure() {
        install(RouteModule())
        bind(ApplicationEngineEnvironment::class.java).toProvider(ApplicationEngineProvider::class.java).asEagerSingleton()
    }

    @Provides
    fun engine(environment: ApplicationEngineEnvironment): ApplicationEngine {
        return embeddedServer(Netty, environment)
    }
}

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
class ApplicationEngineProvider @Inject constructor(
    @Named("web.port") private val webPort: Int,
    @Named("web.bind") private val bindAddress: String,
    private val objectMapper: ObjectMapper,
    private val routes: java.util.Set<Routes>
) : Provider<ApplicationEngineEnvironment> {

    override fun get(): ApplicationEngineEnvironment {
        return applicationEngineEnvironment {
            module {
                install(DefaultHeaders)
                install(CallLogging) {
                    level = Level.INFO
                }
                install(ContentNegotiation) {
                    register(
                        ContentType.Application.Json,
                        JacksonConverter(objectMapper)
                    )
                }
                routing {
                    for (route in routes) {
                        route.config().invoke(this)
                    }
                }
                connector {
                    port = webPort
                    host = bindAddress
                }
            }
        }
    }
}