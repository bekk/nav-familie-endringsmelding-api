package no.nav.familie.endringsmelding.endringsmelding

data class Endringsmelding(
        val tekst: String,
        val dokumenter: List<String>? = emptyList()
)