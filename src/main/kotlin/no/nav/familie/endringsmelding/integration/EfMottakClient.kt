package no.nav.familie.endringsmelding.integration

import no.nav.familie.endringsmelding.config.EfMottakConfig
import no.nav.familie.endringsmelding.integration.dto.KvitteringDto
import no.nav.familie.http.client.AbstractPingableRestClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestOperations
import java.net.URI

@Service
class EfMottakClient(
    private val config: EfMottakConfig,
    @Qualifier("tokenExchange") operations: RestOperations,
) :
    AbstractPingableRestClient(operations, "ef.innsending") {

    override val pingUri: URI = config.pingUri

    fun sendInn(endringsmelding: String): KvitteringDto {
        return postForEntity(config.sendEfEndringsmeldingUri, endringsmelding)
    }
}
