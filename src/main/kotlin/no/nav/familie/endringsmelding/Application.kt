package no.nav.familie.endringsmelding

import no.nav.security.token.support.spring.api.EnableJwtTokenValidation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = ["no.nav.familie.endringsmelding"])
@EnableJwtTokenValidation(ignore = ["org.springframework", "org.springdoc"])
class Application

fun main(args: Array<String>) {
    SpringApplicationBuilder(Application::class.java).run(*args)
}
