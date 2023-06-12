package no.nav.familie.endringsmelding.api.dto

import java.time.LocalDateTime

data class Kvittering(val text: String, val mottattDato: LocalDateTime?)
