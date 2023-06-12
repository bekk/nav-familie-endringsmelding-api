package no.nav.familie.endringsmelding.service

import no.nav.familie.endringsmelding.integration.PdlClient
import no.nav.familie.endringsmelding.integration.dto.pdl.visningsnavn
import no.nav.familie.sikkerhet.EksternBrukerUtils
import org.springframework.stereotype.Service

@Service
class OppslagService(private val pdlClient: PdlClient) {

    fun hentSøkerNavn(): String {
        val søkersPersonIdent = EksternBrukerUtils.hentFnrFraToken()
        val pdlSøker = pdlClient.hentSøker(søkersPersonIdent)
        return pdlSøker.navn.first().visningsnavn()
    }
}
