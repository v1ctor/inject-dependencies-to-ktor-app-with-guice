package org.buldakov.ktor.guice.example.modules

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Provider
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import org.buldakov.ktor.guice.example.routes.Routes
import org.slf4j.event.Level
import javax.inject.Named

class ApplicationModule : AbstractModule() {
    override fun configure() {
        install(RouteModule())
        bind(ApplicationEngineEnvironment::class.java).toProvider(ApplicationEngineEnvironmentProvider::class.java)
            .asEagerSingleton()
    }
}

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
class ApplicationEngineEnvironmentProvider @Inject constructor(
    @Named("web.port") private val webPort: Int,
    @Named("web.bind") private val bindAddress: String,
    @Named("api") private val objectMapper: ObjectMapper,
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
            }
            connector {
                host = bindAddress
                port = webPort
            }
        }
    }
}