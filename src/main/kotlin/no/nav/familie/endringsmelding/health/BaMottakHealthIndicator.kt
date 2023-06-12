package no.nav.familie.endringsmelding.health

import no.nav.familie.endringsmelding.integration.EfMottakClient
import no.nav.familie.http.health.AbstractHealthIndicator
import org.springframework.stereotype.Component

@Component
internal class BaMottakHealthIndicator(efMottakClient: EfMottakClient) :
    AbstractHealthIndicator(efMottakClient, "familie.ba.mottak")
