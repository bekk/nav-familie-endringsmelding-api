package no.nav.familie.endringsmelding.mock

import io.mockk.every
import io.mockk.mockk
import no.nav.familie.endringsmelding.integration.BaMottakClient
import no.nav.familie.endringsmelding.integration.EfMottakClient
import no.nav.familie.endringsmelding.integration.KsMottakClient
import no.nav.familie.endringsmelding.integration.dto.KvitteringDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration // TODO: Slett når vi ikke bruker fly.io
@Profile("fly")
class MottakClientMockFly {

    @Bean
    fun efMottakClient(): EfMottakClient {
        val efMottakClient: EfMottakClient = mockk()

        every { efMottakClient.sendInn(any()) } returns KvitteringDto("OK MOCK")
        every { efMottakClient.ping() } returns Unit

        return efMottakClient
    }

    @Bean
    @Primary
    fun baMottakClient(): BaMottakClient {
        val baMottakClient: BaMottakClient = mockk()

        every { baMottakClient.sendInn(any()) } returns KvitteringDto("OK MOCK")
        every { baMottakClient.ping() } returns Unit

        return baMottakClient
    }

    @Bean
    @Primary
    fun ksMottakClient(): KsMottakClient {
        val ksMottakClient: KsMottakClient = mockk()

        every { ksMottakClient.sendInn(any()) } returns KvitteringDto("OK MOCK KS")
        every { ksMottakClient.ping() } returns Unit

        return ksMottakClient
    }
}
