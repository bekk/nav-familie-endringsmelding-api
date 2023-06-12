package no.nav.familie.endringsmelding.integration.pdl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PdlDtoTest {

    @Test
    fun `pdlSøkerData inneholder samme felter som blir spurt om i query`() {
        val spørringsfelter = PdlTestUtil.parseSpørring("/pdl/søker.graphql")

        val dtoFelter = PdlTestUtil.finnFeltStruktur(PdlTestdata.pdlSøkerData)!!

        assertThat(dtoFelter).isEqualTo(spørringsfelter["data"])
    }
}
