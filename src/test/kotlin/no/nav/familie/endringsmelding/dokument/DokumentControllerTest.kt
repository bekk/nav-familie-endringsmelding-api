package no.nav.familie.endringsmelding.dokument

import no.nav.familie.endringsmelding.integrationTest.OppslagSpringRunnerTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.assertj.core.api.Assertions
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.io.File


class DokumentControllerTest : OppslagSpringRunnerTest(){

    val tokenSubject = "12345678911"
    @BeforeEach
    fun førAlle() {
        headers.setBearerAuth(søkerBearerToken(tokenSubject))
        headers.contentType = MediaType.MULTIPART_FORM_DATA
    }

    @Test
    fun `skal kunne laste opp dokument`() {

        /**
        val nyFil = MockMultipartFile("minfil","minfil","application/pdf", "Eg heiter Kjetil!!!".toByteArray())

        val requestMap = LinkedMultiValueMap<String, Any>()
        //requestMap.add("file", ByteArrayResource("test".toByteArray()))
        requestMap.add("file", File("/Users/kjetil/Documents/Kotlin Language Features Map.pdf"))

        val requestEntity = HttpEntity<MultiValueMap<String, Any>>(requestMap, headers)

        /**
         *
        val response = restTemplate.exchange<Map<String, String>>(
            localhost("/api/dokument"),
            HttpMethod.POST,
            HttpEntity(requestMap, headers),
        )
         */

        val response2 = restTemplate.postForEntity<Map<String, String>>(localhost("/api/dokument"), requestEntity)
        println(response2.toString())

        Assertions.assertThat(response2.statusCodeValue).isEqualTo(HttpStatus.CREATED_201)
        Assertions.assertThat(response2.body?.get("dokumentId")).isNotNull()
        Assertions.assertThat(response2.body?.get("filnavn")).isEqualTo("minfil")

        */

    }
}