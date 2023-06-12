package no.nav.familie.endringsmelding.api

import org.springframework.http.HttpStatus

data class ApiFeil(val feil: String, val httpStatus: HttpStatus) : RuntimeException()
