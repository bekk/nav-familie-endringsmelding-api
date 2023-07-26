package no.nav.familie.endringsmelding.endringsmelding

//TODO: Kan man gjøre dette på en lurere måte?
data class EndringsmeldingPayload(
        val tekst: String,
        val dokumenter: String
)

data class EndringsmeldingDto(
        val tekst: String,
        val dokumenter: List<String>? = emptyList()
)