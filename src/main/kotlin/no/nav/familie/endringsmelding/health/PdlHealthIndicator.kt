package no.nav.familie.endringsmelding.health

import no.nav.familie.endringsmelding.integration.PdlClient
import no.nav.familie.http.health.AbstractHealthIndicator
import org.springframework.stereotype.Component

@Component
internal class PdlHealthIndicator(pdlClient: PdlClient) :
    AbstractHealthIndicator(pdlClient, "pdl.personinfo")
