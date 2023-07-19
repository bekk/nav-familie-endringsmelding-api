package no.nav.familie.endringsmelding.endringsmelding

import no.nav.familie.endringsmelding.api.dto.Kvittering
import no.nav.familie.endringsmelding.integration.BaMottakClient
import no.nav.familie.endringsmelding.integration.EfMottakClient
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EndringsmeldingService(
    private val efMottakClient: EfMottakClient,
    private val baMottakClient: BaMottakClient,
) {

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
        val kvittering = baMottakClient.sendInn(endringsmelding)
        return Kvittering(kvittering.text, innsendingMottatt)
    }
}
