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

    val SPESIAL_TEGN_REGEX = "/[!@#\$%^&*()?\"{}|<>+¨=]/"
    fun validerEndringsmelding(endringsmelding: String){
        if(endringsmelding.isEmpty()){
            throw ApiFeil(EndringsmeldingFeil.MANGLER_TEKST.toString(), HttpStatus.BAD_REQUEST)
        }

        if(endringsmelding.length > 1000){
            throw ApiFeil(EndringsmeldingFeil.OVER_MAKS_LENGDE.toString(), HttpStatus.BAD_REQUEST)
        }

        if(endringsmelding.length < 9){
            throw ApiFeil(EndringsmeldingFeil.MINDRE_ENN_TI_TEGN.toString(), HttpStatus.BAD_REQUEST)
        }

        if(endringsmelding.matches(SPESIAL_TEGN_REGEX.toRegex())){
            throw ApiFeil(EndringsmeldingFeil.HAR_SPESIAL_TEGN.toString(), HttpStatus.BAD_REQUEST)
        }
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
