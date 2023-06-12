package no.nav.familie.endringsmelding.integration.dto.pdl

fun Navn.visningsnavn(): String {
    return if (mellomnavn.isNullOrEmpty()) {
        "$fornavn $etternavn"
    } else {
        "$fornavn $mellomnavn $etternavn"
    }
}
