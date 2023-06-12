package no.nav.familie.endringsmelding.exception

open class PdlRequestException(melding: String? = null) : Exception(melding)
class PdlNotFoundException : PdlRequestException()
