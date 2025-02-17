[![CI](https://github.com/mpalourdio/SpringBootTemplate/actions/workflows/main.yml/badge.svg)](https://github.com/mpalourdio/SpringBootTemplate/actions/workflows/main.yml)

Spring Boot 3.x example application.
=======================================

More a reminder for some dirty tricks than anything else...

Include examples for :
- @SpringBootTest
- @DataJpaTest
- @AutoConfigureMockMvc
- JUnit & Mockito 2
- EventListener : non void ``@EventListener`` method triggers automatically another event
- Async Event (Annotation Driven)
- Request Forwarding
- DataSource auto-configuration
- Thymeleaf
- HttpSecurity Cache Control
- Externalized configuration
- Travis CI Build
- Custom logging configuration
- Logout example
- GZip compression for some content-type
- org.hibernate.java8 to handle LocalDate API in entities
- Mock EntityManager (@PersistenceContext)
- File Download with HttpServletResponse and ResponseEntity
- Excel Generation with Apache POI
- Basic auth protected endpoint (/basicauth) and how to test this kind of (silly) protection (lol)
- Async static Logger event driven
- Spring Cloud configuration server integration
- Refreshable controller with @RefreshScope
- Mockito 2 integration with Strict Stubs checking
- @EnableConfigurationProperties examples
- JSON Serialization/Unserialization examples
- Custom MediaType
- ...

# DB
`docker run -it --name postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=taskmanager -p 5432:5432 -d postgres`
