package no.nav.familie.endringsmelding.service

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

    var SPESIAL_TEGN_REGEX = "/[!@#\$%^&*()?\"{}|<>+¨=]/";
    fun validerEndringsmelding(endringsmelding: String):String{
        if(endringsmelding.length < 9){
            println("For få tegn")
            return "For få tegn"
        }

        if(endringsmelding.length > 1000){
            println("For mange tegn")
            return "For mange tegn";
        }

        if(!endringsmelding.matches(SPESIAL_TEGN_REGEX.toRegex())){
            println("Inneholder spesialtegn")
            return "Inneholder spesialtegn";
        }

        println("Validert Riktig");
        return "";
    }

    fun sendInnEf(
        endringsmelding: String,
        innsendingMottatt: LocalDateTime,
    ): Kvittering {
        val kvittering = efMottakClient.sendInn(endringsmelding)
        return Kvittering(kvittering.text, innsendingMottatt)
    }

    fun sendInnBa(
        endringsmelding: String,
        innsendingMottatt: LocalDateTime,
    ): Kvittering {
        val valideringsFeil  = validerEndringsmelding(endringsmelding);
        if(!valideringsFeil.isEmpty()){
            throw ApiFeil(valideringsFeil, HttpStatus.BAD_REQUEST)
        }
        val kvittering = baMottakClient.sendInn(endringsmelding)
        return Kvittering(kvittering.text, innsendingMottatt)
    }
}
