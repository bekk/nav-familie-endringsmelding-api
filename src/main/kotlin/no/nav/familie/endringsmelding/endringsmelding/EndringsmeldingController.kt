package no.nav.familie.endringsmelding.endringsmelding

import no.nav.familie.endringsmelding.api.ApiFeil
import no.nav.familie.endringsmelding.api.dto.Kvittering
import no.nav.familie.endringsmelding.featuretoggle.FeatureToggleService
import no.nav.familie.sikkerhet.EksternBrukerUtils
import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.core.api.RequiredIssuers
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping(path = ["/api/send-inn"], produces = [APPLICATION_JSON_VALUE], consumes = [APPLICATION_JSON_VALUE])
@RequiredIssuers(
    ProtectedWithClaims(issuer = EksternBrukerUtils.ISSUER_TOKENX, claimMap = ["acr=Level4"]),
)
@Validated
class EndringsmeldingController(val endringsmeldingService: EndringsmeldingService, val featureToggleService: FeatureToggleService) {

    @PostMapping(path = ["/ba"])
    fun sendInn(@RequestBody endringsmelding: EndringsmeldingPayload): Kvittering {
        if (!featureToggleService.isEnabled("familie.endringsmelding.send-inn")) {
            throw ApiFeil("Kan ikke sende inn endringsmelding - funksjonen er ikke aktivert", HttpStatus.BAD_REQUEST)
        }
        val endringsmeldingDto = EndringsmeldingDto(tekst = endringsmelding.tekst, dokumenter = endringsmelding.dokumenter.split(","))
        println("Dokumenter som skal bli sendt videre herifra" + endringsmeldingDto.dokumenter)

        val innsendingMottatt = LocalDateTime.now()
        return endringsmeldingService.sendInnBa(endringsmeldingDto.tekst, innsendingMottatt)
    }

    @PostMapping(path = ["/ks"])
    fun sendInnKs(@RequestBody endringsmelding: EndringsmeldingPayload): Kvittering {
        if (!featureToggleService.isEnabled("familie.endringsmelding.send-inn")) {
            throw ApiFeil("Kan ikke sende inn endringsmelding - funksjonen er ikke aktivert", HttpStatus.BAD_REQUEST)
        }
        val endringsmeldingDto = EndringsmeldingDto(tekst = endringsmelding.tekst, dokumenter = endringsmelding.dokumenter.split(","))
        println("Dokumenter som skal bli sendt videre herifra" + endringsmeldingDto.dokumenter)

        val innsendingMottatt = LocalDateTime.now()
        return endringsmeldingService.sendInnKs(endringsmeldingDto.tekst, innsendingMottatt)
    }
}
