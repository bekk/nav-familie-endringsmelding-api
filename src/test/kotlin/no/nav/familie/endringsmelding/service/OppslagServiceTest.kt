package no.nav.familie.endringsmelding.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import no.nav.familie.endringsmelding.integration.PdlClient
import no.nav.familie.endringsmelding.integration.dto.pdl.Adressebeskyttelse
import no.nav.familie.endringsmelding.integration.dto.pdl.AdressebeskyttelseGradering
import no.nav.familie.endringsmelding.integration.dto.pdl.AdressebeskyttelseGradering.UGRADERT
import no.nav.familie.endringsmelding.integration.dto.pdl.Navn
import no.nav.familie.endringsmelding.integration.dto.pdl.PdlSøker
import no.nav.familie.endringsmelding.integration.dto.pdl.Sivilstand
import no.nav.familie.endringsmelding.integration.dto.pdl.Sivilstandstype
import no.nav.familie.sikkerhet.EksternBrukerUtils
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OppslagServiceTest {

    val pdlClient: PdlClient = mockk()

    private val oppslagService = OppslagService(
        pdlClient,
    )

    @BeforeEach
    fun setUp() {
        mockkObject(EksternBrukerUtils)
        every { EksternBrukerUtils.hentFnrFraToken() } returns "12345678911"
        mockHentPersonPdlClient()
    }

    @AfterEach
    internal fun tearDown() {
        unmockkObject(EksternBrukerUtils)
    }

    @Test
    fun `skal hente ut navn og fnr til søker`() {
        oppslagService.hentSøkerNavn()
        // TODO: Implementer sjekk
    }

    private fun mockHentPersonPdlClient(
        fornavn: String = "TestNavn",
        mellomnavn: String = "TestNavn",
        etternavn: String = "TestNavn",
        adressebeskyttelseGradering: AdressebeskyttelseGradering = UGRADERT,
    ) {
        every { pdlClient.hentSøker(any()) } returns (
            PdlSøker(
                listOf(Adressebeskyttelse(adressebeskyttelseGradering)),
                listOf(),
                listOf(),
                navn = listOf(Navn(fornavn, mellomnavn, etternavn)),
                sivilstand = listOf(Sivilstand(Sivilstandstype.UOPPGITT)),
                listOf(),
            )
            )
    }
}
