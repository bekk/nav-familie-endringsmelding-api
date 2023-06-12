package no.nav.familie.endringsmelding.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@Suppress("ArrayInDataClass")
@ConfigurationProperties(prefix = "cors")
@ConstructorBinding
internal data class CorsProperties(val allowedOrigins: Array<String>)
