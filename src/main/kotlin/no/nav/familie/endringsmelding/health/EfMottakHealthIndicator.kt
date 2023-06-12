package no.nav.familie.endringsmelding.health

import no.nav.familie.endringsmelding.integration.EfMottakClient
import no.nav.familie.http.health.AbstractHealthIndicator
import org.springframework.stereotype.Component

@Component
internal class EfMottakHealthIndicator(efMottakClient: EfMottakClient) :
    AbstractHealthIndicator(efMottakClient, "familie.ef.mottak")
