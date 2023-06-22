package no.nav.familie.endringsmelding.mock

import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import java.lang.annotation.Inherited

@MustBeDocumented
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(
    MockOauth2ServerConfig::class,
)
@Profile("fly")
annotation class EnableMockOAuth2Server()