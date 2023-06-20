package no.nav.familie.endringsmelding.api

import no.nav.familie.endringsmelding.api.dto.PersonMinimumDto
import no.nav.familie.endringsmelding.service.OppslagService
import no.nav.familie.sikkerhet.EksternBrukerUtils
import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.core.api.RequiredIssuers
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [OppslagController.OPPSLAG], produces = [APPLICATION_JSON_VALUE])
@RequiredIssuers(
    ProtectedWithClaims(issuer = EksternBrukerUtils.ISSUER_TOKENX, claimMap = ["acr=Level4"]),
)
@Validated
class OppslagController(val oppslagService: OppslagService) {

    @GetMapping("/soker")
    fun søkerinfominimum(): PersonMinimumDto {
        val søkerNavn = oppslagService.hentSøkerNavn()
        return søkerNavn.tilSøkerMinimumDto()
    }

    companion object {

        const val OPPSLAG = "/api/oppslag"
    }

    private fun String.tilSøkerMinimumDto(): PersonMinimumDto {
        return PersonMinimumDto(EksternBrukerUtils.hentFnrFraToken(), this)
    }
}
