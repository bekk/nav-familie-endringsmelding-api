package no.nav.familie.endringsmelding.api

import no.nav.familie.endringsmelding.api.dto.Kvittering
import no.nav.familie.endringsmelding.featuretoggle.FeatureToggleService
import no.nav.familie.endringsmelding.service.EndringsmeldingService
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
@RequestMapping(path = ["/api/send-inn"], produces = [APPLICATION_JSON_VALUE])
@RequiredIssuers(
    ProtectedWithClaims(issuer = EksternBrukerUtils.ISSUER_TOKENX, claimMap = ["acr=Level4"]),
    ProtectedWithClaims(issuer = EksternBrukerUtils.ISSUER_SELVBETJENING, claimMap = ["acr=Level4"]),
)
@Validated
class InnsendingController(val endringsmeldingService: EndringsmeldingService, val featureToggleService: FeatureToggleService) {

    @PostMapping(path = ["/ba"])
    fun sendInn(@RequestBody endringsmelding: String): Kvittering {
        if (!featureToggleService.isEnabled("familie.endringsmelding.send-inn")) {
            throw ApiFeil("Kan ikke sende inn endringsmelding - funksjonen er ikke aktivert", HttpStatus.BAD_REQUEST)
        }
        val innsendingMottatt = LocalDateTime.now()
        return endringsmeldingService.sendInnBa(endringsmelding, innsendingMottatt)
    }
}
