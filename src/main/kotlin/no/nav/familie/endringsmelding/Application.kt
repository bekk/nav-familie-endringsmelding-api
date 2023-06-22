package no.nav.familie.endringsmelding

import no.nav.familie.endringsmelding.mock.EnableMockOAuth2ServerFly
import no.nav.security.token.support.spring.api.EnableJwtTokenValidation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationPropertiesScan
@EnableMockOAuth2ServerFly // TODO: SLett når vi ikke bruker fly.io
@SpringBootApplication(scanBasePackages = ["no.nav.familie.endringsmelding"])
@EnableJwtTokenValidation(ignore = ["org.springframework", "org.springdoc"])
class Application

fun main(args: Array<String>) {
    SpringApplicationBuilder(Application::class.java).run(*args)
}
