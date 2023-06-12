package no.nav.familie.endringsmelding.api

import no.nav.familie.endringsmelding.featuretoggle.FeatureToggleService
import no.nav.security.token.support.core.api.Unprotected
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/featuretoggle"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Unprotected
class FeatureToggleController(private val featureToggleService: FeatureToggleService) {

    val funksjonsbrytere = listOf(
        "familie.endringsmelding.send-inn",
    )

    @GetMapping
    fun sjekkAlle(): Map<String, Boolean> {
        return funksjonsbrytere.associateWith { featureToggleService.isEnabled(it) }
    }

    @GetMapping("/{toggleId}")
    fun sjekkFunksjonsbryter(
        @PathVariable toggleId: String,
        @RequestParam("defaultverdi") defaultVerdi: Boolean? = false,
    ): Boolean {
        return featureToggleService.isEnabled(toggleId, defaultVerdi ?: false)
    }
}
