package no.nav.familie.endringsmelding.endringsmelding

import io.mockk.mockk
import no.nav.familie.endringsmelding.api.ApiFeil
import no.nav.familie.endringsmelding.endringsmelding.EndringsmeldingFeil.HAR_SPESIAL_TEGN
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus.BAD_REQUEST

class EndringsmeldingServiceTest {

    @Test
    fun `skal validere med alle godkjente tegn`() {
        val endringsmeldingService = EndringsmeldingService(mockk(), mockk())
        endringsmeldingService.validerEndringsmelding("Denne skal fungere: Hvorfor? @charlie.  Flere tegn: ÆØÅ.,%. Tall: 1234567890")
    }

    @Test
    fun `skal feile med ingen tekst`() {
        val endringsmeldingService = EndringsmeldingService(mockk(), mockk())

        val feil = assertThrows<ApiFeil> {
            endringsmeldingService.validerEndringsmelding("")
        }

        assertThat(feil.httpStatus).isEqualTo(BAD_REQUEST)
        assertThat(feil.feil).isEqualTo(EndringsmeldingFeil.MANGLER_TEKST.toString())
    }

    @Test
    fun `skal feile med tekst mer enn 1000 tegn`() {
        val endringsmeldingService = EndringsmeldingService(mockk(), mockk())

        val FOR_LANG_MELDING = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed consectetur a ex quis ultricies. Donec tincidunt fermentum eros, ut efficitur massa dictum eu. Morbi ac pellentesque justo. Integer cursus magna metus. Pellentesque eleifend enim est, sed semper ante pretium nec. Nulla scelerisque, elit sed varius hendrerit, urna ex rutrum urna, vel malesuada diam justo et eros. Maecenas et ornare mi. Nam pharetra in elit quis eleifend. Vestibulum mollis nisi eu quam pharetra, vel vestibulum diam consequat. Vivamus commodo, libero consequat laoreet pellentesque, nisi quam ultricies dui, id egestas mauris urna at quam. Aliquam ipsum ligula, venenatis eu gravida ac, luctus pharetra elit. Nunc feugiat nibh rhoncus arcu sagittis, et sodales sapien elementum. Sed bibendum lectus sed magna maximus vestibulum.

            Vestibulum lobortis sit amet est et elementum. Aliquam maximus eu orci vitae imperdiet. In hendrerit risus lacus, ut venenatis sem consectetur sed. Morbi vehicula sodales sagittis. Phasellus hendrerit auctor arcu. Integer eu nisi in felis porta fermentum. Pellentesque ultrices risus felis, nec blandit nibh venenatis at. Pellentesque ullamcorper ultrices sagittis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Quisque aliquam massa in eros imperdiet suscipit. Cras gravida lorem nec rutrum lobortis. Vestibulum in felis id mi faucibus hendrerit. Integer tincidunt nunc nec augue molestie, id dictum mi facilisis. Cras tortor lectus, suscipit vel sollicitudin at, hendrerit eu ipsum. Nullam est ligula, faucibus et neque quis, auctor rutrum ligula. Ut congue lacus fringilla nibh dictum molestie.

            Nulla turpis lorem, lacinia vitae quam et, lacinia dignissim metus. Quisque eget efficitur urna. Ut aliquam ultrices nibh a feugiat. Sed ac libero eget nisl molestie volutpat. Pellentesque vitae quam vel elit hendrerit tristique sed id quam. Donec eget ante nunc. Nunc non tellus euismod, porta elit eget, auctor est. Nam a nibh malesuada, sollicitudin tellus a, faucibus ex.

            Ut auctor, arcu ac lacinia sodales, turpis enim lacinia enim, condimentum placerat magna nisl sit amet arcu. Sed et aliquam dolor. Aenean et ornare quam. Integer gravida vel ex quis ultrices. Aliquam id pretium nulla. In a libero neque. Etiam est risus, rutrum ac risus in, interdum commodo velit. Mauris ex dolor, volutpat at consequat a, feugiat in neque. Donec suscipit quam sem, volutpat ultrices quam ornare finibus. Suspendisse commodo, dolor dapibus luctus venenatis, purus sapien vehicula lacus, sed dictum nisl arcu sed tortor. Morbi rhoncus odio ac libero consequat vulputate. Nullam blandit quam est, quis malesuada enim tincidunt id. Nunc eu pharetra nulla.

            Cras id aliquam enim. Vivamus dictum tortor ante, eget aliquet lectus venenatis ac. Phasellus eleifend enim vitae ex convallis, nec congue lectus molestie. Nunc pharetra lobortis eros id viverra. Morbi a consequat erat, nec ultrices ante. Aenean nunc elit, mollis nec pellentesque at, finibus eget justo. Nulla nec leo felis. Sed venenatis ante eget ex placerat, porta gravida libero vestibulum.

            In ac metus eu lacus tempor suscipit. Praesent justo quam, lobortis id leo in, lacinia efficitur neque. Nunc metus odio, pretium sed ornare at, placerat ac leo. Nullam sodales sodales pharetra. Pellentesque euismod facilisis diam nec bibendum. Aliquam odio urna, varius at tellus vitae, ultricies suscipit ipsum. Ut accumsan hendrerit lacus, vehicula consectetur purus feugiat eget.

            Aenean posuere nunc eu purus vehicula aliquet. Sed vel egestas est. Cras placerat pretium enim, eu tempor odio sagittis et. In ligula justo, ultricies sit amet tincidunt at, ultrices ut nisl. Proin quis risus auctor, rutrum enim a, fermentum massa. Maecenas ornare non velit eget lacinia. Nullam nulla nulla, aliquam ac aliquam sollicitudin, ultrices venenatis leo. Donec ac nulla nunc. Maecenas tempus pharetra tempor.

            In quis lacus velit. Sed bibendum, diam non finibus ultrices, arcu nibh consequat tellus, et tincidunt augue ligula vel justo. Nulla accumsan rhoncus aliquet. Vivamus scelerisque non lectus nec interdum. Aenean aliquet nisl felis, non rutrum arcu sollicitudin ac. Cras porttitor sapien at mi cursus, sed accumsan sapien iaculis. Etiam efficitur risus venenatis ultrices dictum. Duis laoreet lorem massa, nec ullamcorper dui semper at. Nulla ac enim ipsum. Ut gravida tellus et nibh pharetra fringilla. Phasellus venenatis non tellus non malesuada. Pellentesque commodo, nisl vel luctus elementum, est nulla mattis metus, at mollis neque augue ut nunc. Sed enim dolor, commodo non magna et, vestibulum euismod felis.
            
        """.trimIndent()

        val feil = assertThrows<ApiFeil> {
            endringsmeldingService.validerEndringsmelding(FOR_LANG_MELDING)
        }

        assertThat(feil.httpStatus).isEqualTo(BAD_REQUEST)
        assertThat(feil.feil).isEqualTo(EndringsmeldingFeil.OVER_MAKS_LENGDE.toString())
    }

    @Test
    fun `skal feile med tekst mindre enn 10 tegn`() {
        val endringsmeldingService = EndringsmeldingService(mockk(), mockk())

        val feil = assertThrows<ApiFeil> {
            endringsmeldingService.validerEndringsmelding("123456789")
        }

        assertThat(feil.httpStatus).isEqualTo(BAD_REQUEST)
        assertThat(feil.feil).isEqualTo(EndringsmeldingFeil.MINDRE_ENN_TI_TEGN.toString())
    }

    @Test
    fun `skal feile med spesialtegn som ikke er tillat`() {
        val endringsmeldingService = EndringsmeldingService(mockk(), mockk())

        val feil = assertThrows<ApiFeil> {
            endringsmeldingService.validerEndringsmelding("Denne ikke () skal fungere")
        }

        assertThat(feil.httpStatus).isEqualTo(BAD_REQUEST)
        assertThat(feil.feil).isEqualTo(HAR_SPESIAL_TEGN.toString())
    }
}
