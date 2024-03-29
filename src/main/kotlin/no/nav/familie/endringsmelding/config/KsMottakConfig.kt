package no.nav.familie.endringsmelding.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@ConfigurationProperties("familie.ks.mottak")
@ConstructorBinding
data class KsMottakConfig(val uri: URI) {

    internal val sendKsEndringsmeldingUri = byggUri(PATH_SEND_INN_ENDRINGSMELDING)

    internal val pingUri = byggUri(PATH_PING)

    private fun byggUri(path: String) = UriComponentsBuilder.fromUri(uri).path(path).build().toUri()

    companion object {

        private const val PATH_SEND_INN_ENDRINGSMELDING = "/endringsmelding"
        private const val PATH_PING = "/ping"
    }
}
