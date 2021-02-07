package org.buldakov.ktor.guice.example.modules

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.AbstractModule
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
        bind(ApplicationEngine::class.java).toProvider(ApplicationEngineProvider::class.java).asEagerSingleton()
    }
}

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
class ApplicationEngineProvider @Inject constructor(
    @Named("web.port") private val webPort: Int,
    @Named("web.bind") private val bindAddress: String,
    private val objectMapper: ObjectMapper,
    private val routes: java.util.Set<Routes>
) : Provider<ApplicationEngine> {

    override fun get(): ApplicationEngine {
        return embeddedServer(Netty, host = bindAddress, port = webPort) {
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
    }
}