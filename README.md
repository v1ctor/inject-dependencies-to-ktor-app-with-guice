## About
This project was created as an example for the blog post https://handspringer.medium.com/kotlin-web-service-using-ktor-guice-and-jackson-cf6835d2c35b


## Start demo application 

```sh
./gradlew run
```

## Send requests to API
```sh
curl localhost:8080/greet
{
  "message" : "Hello World!"
}
```

```sh
curl localhost:8080/status
{
  "status" : "OK"
}
```
