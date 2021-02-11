package org.buldakov.ktor.guice.example.providers

class GreetingProvider : IGreetingProvider{

    override fun getGreeting(): String {
        return "Hello World!"
    }

}