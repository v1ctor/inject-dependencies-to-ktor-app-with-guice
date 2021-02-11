import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Key
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.testing.*
import org.buldakov.ktor.guice.example.modules.ApplicationModule
import org.buldakov.ktor.guice.example.modules.JsonModule
import org.buldakov.ktor.guice.example.modules.PropertyModule
import org.buldakov.ktor.guice.example.providers.IGreetingProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.test.assertEquals


class MockProviderModule : AbstractModule() {

    private fun <T> bindMock(clazz: Class<T>) {
        bind(clazz).toInstance(Mockito.mock(clazz))
    }

    override fun configure() {
        bindMock(IGreetingProvider::class.java)
    }
}

class ApiTest {

    private var env: ApplicationEngineEnvironment? = null

    private var mapper: ObjectMapper? = null
    private var greetingProvider: IGreetingProvider? = null

    @BeforeEach
    fun setup() {
        val modules = listOf(
            PropertyModule(),
            ApplicationModule(),
            JsonModule(),
            // Mocks
            MockProviderModule(),
        )

        val injector = Guice.createInjector(modules)
        env = injector.getInstance(ApplicationEngineEnvironment::class.java)
        mapper = injector.getInstance(Key.get(ObjectMapper::class.java))
        greetingProvider = injector.getInstance(IGreetingProvider::class.java)
    }

    @Test
    fun statusApiTest() = withApplication(env!!) {

        with(handleRequest(HttpMethod.Get, "/status")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(
                mapOf("status" to "OK"),
                mapper?.readValue(response.content, Map::class.java)
            )
        }
    }

    @Test
    fun greetingApiTest() = withApplication(env!!) {

        val greeting = "Hello, my name is Inigo Montoya. You killed my father. Prepare to die."
        Mockito.`when`(greetingProvider?.getGreeting())
            .thenReturn(greeting)

        with(handleRequest(HttpMethod.Get, "/greet")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(
                mapOf("message" to greeting),
                mapper?.readValue(response.content, Map::class.java)
            )
        }
    }

}

