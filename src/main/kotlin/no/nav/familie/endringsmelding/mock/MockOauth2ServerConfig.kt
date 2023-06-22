package no.nav.familie.endringsmelding.mock

import no.nav.familie.endringsmelding.config.ApplicationConfig
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.OAuth2Config
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import no.nav.security.mock.oauth2.token.OAuth2TokenProvider
import no.nav.security.token.support.core.configuration.ProxyAwareResourceRetriever
import no.nav.security.token.support.spring.EnableJwtTokenValidationConfiguration
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@Configuration // TODO: Slett når vi ikke bruker fly.io
@Profile("fly")
@AutoConfigureOrder(0)
@Order(0)
@AutoConfigureBefore(EnableJwtTokenValidationConfiguration::class)
class MockOauth2ServerConfig {
    private lateinit var mockOAuth2Server: MockOAuth2Server
    private val logger = LoggerFactory.getLogger(ApplicationConfig::class.java)

    init {
        mockOAuth2Server = MockOAuth2Server(
            OAuth2Config(true, null, OAuth2TokenProvider(), setOf(DefaultOAuth2TokenCallback())),
        )
    }

    @Bean
    @Primary
    @DependsOn("mockOAuth2Server")
    fun overrideOidcResourceRetriever(): ProxyAwareResourceRetriever? {
        return ProxyAwareResourceRetriever()
    }

    @Bean
    fun mockOAuth2Server(): MockOAuth2Server? {
        logger.info("initializing mockOauth2Server")
        return mockOAuth2Server
    }

    @PostConstruct
    fun start() {
        logger.info("starting mockOauth2Server")
        val port = 11499
        mockOAuth2Server?.start(port)
    }

    @PreDestroy
    fun shutdown() {
        mockOAuth2Server?.shutdown()
    }
}
