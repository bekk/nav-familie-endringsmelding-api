package no.nav.familie.endringsmelding.api

import io.mockk.every
import io.mockk.mockk
import no.nav.familie.endringsmelding.api.dto.Kvittering
import no.nav.familie.endringsmelding.endringsmelding.EndringsmeldingService
import no.nav.familie.endringsmelding.featuretoggle.FeatureToggleService
import no.nav.familie.endringsmelding.integrationTest.OppslagSpringRunnerTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.exchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@Profile("send-inn-controller-test")
@Configuration
class EndringsmeldingControllerTestConfiguration {


    @Primary
    @Bean
    fun featureToggleService(): FeatureToggleService = mockk()
}

@ActiveProfiles("send-inn-controller-test")
internal class EndringsmeldingControllerTest : OppslagSpringRunnerTest() {

    @Autowired
    lateinit var featureToggleService: FeatureToggleService

    val tokenSubject = "12345678911"

    @BeforeEach
    fun førAlle() {
        headers.setBearerAuth(søkerBearerToken(tokenSubject))
    }

    @Test
    fun `sendInn returnerer kvittering riktig kvittering med riktig Bearer token for BA`() {
        val endringsmelding = "xyz"

        every { featureToggleService.isEnabled(any()) } returns true

        val response = restTemplate.exchange<Kvittering>(
            localhost("/api/send-inn/ba"),
            HttpMethod.POST,
            HttpEntity(endringsmelding, headers),
        )

        assertThat(response.statusCodeValue).isEqualTo(200)
        assertThat(response.body?.text).isEqualTo("OK MOCK")
    }

    @Test
    fun `sendInn returnerer kvittering riktig kvittering med riktig Bearer token for KS`() {
        val endringsmelding = "xyz"

        every { featureToggleService.isEnabled(any()) } returns true

        val response = restTemplate.exchange<Kvittering>(
                localhost("/api/send-inn/ks"),
                HttpMethod.POST,
                HttpEntity(endringsmelding, headers),
        )

        assertThat(response.statusCodeValue).isEqualTo(200)
        assertThat(response.body?.text).isEqualTo("OK MOCK KS")
    }
}
