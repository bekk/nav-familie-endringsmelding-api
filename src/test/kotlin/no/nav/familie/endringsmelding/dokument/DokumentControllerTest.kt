package no.nav.familie.endringsmelding.dokument

import no.nav.familie.endringsmelding.integrationTest.OppslagSpringRunnerTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class DokumentControllerTest : OppslagSpringRunnerTest() {

    val tokenSubject = "12345678911"

    @BeforeEach
    fun førAlle() {
        headers.setBearerAuth(søkerBearerToken(tokenSubject))
        headers.contentType = MediaType.MULTIPART_FORM_DATA
    }

    @Test
    fun `skal kunne laste opp dokument`() {
        val filnavn = "minfil"
        val filInnhold = "x".toByteArray()
        val response = lastOppFil(filnavn, filInnhold)
        Assertions.assertThat(response.statusCodeValue).isEqualTo(HttpStatus.CREATED.value())
        Assertions.assertThat(response.body?.get("dokumentId")).isNotNull()
        Assertions.assertThat(response.body?.get("filnavn")).isEqualTo(filnavn)
    }

    fun lastOppFil(filnavn: String, filInnhold: ByteArray): ResponseEntity<Map<String, String>> {

        val filMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        val contentDisposition = ContentDisposition
            .builder("form-data")
            .name("file")
            .filename(filnavn)
            .build()
        filMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())

        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()
        body.add("file", HttpEntity(filInnhold, filMap))

        return restTemplate.exchange<Map<String, String>>(
            localhost("/api/dokument"),
            HttpMethod.POST,
            HttpEntity(body, headers),
            String::class.java
        )
    }
}