package no.nav.familie.endringsmelding.dokument

import no.nav.familie.endringsmelding.api.ApiFeil
import no.nav.familie.sikkerhet.EksternBrukerUtils
import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.core.api.RequiredIssuers
import no.nav.security.token.support.core.api.Unprotected
import no.nav.security.token.support.core.context.TokenValidationContextHolder
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID


@RestController
@RequestMapping(path = ["/api/dokument"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
//@RequiredIssuers(
  //  ProtectedWithClaims(issuer = EksternBrukerUtils.ISSUER_TOKENX, claimMap = ["acr=Level4"]),
//)
@Unprotected
@Profile("fly", "local")
class DokumentController {

    val dokumentMap: MutableMap<String, ByteArray> = HashMap()

    @PostMapping
    fun sendDokument(@RequestParam("file") file : MultipartFile): ResponseEntity<Map<String, String>>{

        if(file.isEmpty){
            throw ApiFeil("Tom fil sendt inn!", HttpStatus.BAD_REQUEST)
        }

        val bytes = file.bytes
        val id = UUID.randomUUID().toString();
        dokumentMap.put(id, bytes)

        return ResponseEntity.status(HttpStatus.CREATED).body(mapOf("dokumentId" to id, "filnavn" to file.originalFilename))
    }


}