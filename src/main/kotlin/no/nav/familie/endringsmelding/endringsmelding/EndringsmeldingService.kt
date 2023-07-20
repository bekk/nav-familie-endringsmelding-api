package no.nav.familie.endringsmelding.endringsmelding

import no.nav.familie.endringsmelding.api.ApiFeil
import no.nav.familie.endringsmelding.api.dto.Kvittering
import no.nav.familie.endringsmelding.integration.BaMottakClient
import no.nav.familie.endringsmelding.integration.EfMottakClient
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EndringsmeldingService(
    private val efMottakClient: EfMottakClient,
    private val baMottakClient: BaMottakClient,
) {

    val SPESIAL_TEGN_REGEX = Regex("^[a-zA-ZæøåÆØÅ0-9.,:!?@% ]+$")

    fun validerEndringsmelding(endringsmelding: String) {
        println("Endringsmelding: " + endringsmelding)

        if (endringsmelding.isEmpty()) {
            throw ApiFeil(EndringsmeldingFeil.MANGLER_TEKST.toString(), HttpStatus.BAD_REQUEST)
        }

        if (endringsmelding.length > 1000) {
            throw ApiFeil(EndringsmeldingFeil.OVER_MAKS_LENGDE.toString(), HttpStatus.BAD_REQUEST)
        }

        if (endringsmelding.length < 10) {
            throw ApiFeil(EndringsmeldingFeil.MINDRE_ENN_TI_TEGN.toString(), HttpStatus.BAD_REQUEST)
        }

        if (!endringsmelding.matches(SPESIAL_TEGN_REGEX)) {
            println("FEIL for regex")
            throw ApiFeil(EndringsmeldingFeil.HAR_SPESIAL_TEGN.toString(), HttpStatus.BAD_REQUEST)
        }

        println("Ingen validerings feil")
    }

    fun sendInnEf(
        endringsmelding: String,
        innsendingMottatt: LocalDateTime,
    ): Kvittering {
        validerEndringsmelding(endringsmelding)
        val kvittering = efMottakClient.sendInn(endringsmelding)
        return Kvittering(kvittering.text, innsendingMottatt)
    }

    fun sendInnBa(
        endringsmelding: String,
        innsendingMottatt: LocalDateTime,
    ): Kvittering {
        validerEndringsmelding(endringsmelding)
        val kvittering = baMottakClient.sendInn(endringsmelding)
        return Kvittering(kvittering.text, innsendingMottatt)
    }
}
