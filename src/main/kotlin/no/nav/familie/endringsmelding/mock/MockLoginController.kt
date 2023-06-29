package no.nav.familie.endringsmelding.mock

import com.nimbusds.jose.JOSEObjectType
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import no.nav.security.token.support.core.api.Unprotected
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController // TODO: Slett når vi ikke bruker fly.io
@RequestMapping(path = ["/local"])
@Profile("fly")
class MockLoginController(val mockOAuth2Server: MockOAuth2Server) {

    @Unprotected
    @GetMapping(path = ["/cookie"])
    fun addCookie(
        @RequestParam("issuerId") issuerId: String,
        @RequestParam("audience") audience: String,
        @RequestParam(value = "subject", defaultValue = "12345678910") subject: String,
        @RequestParam(value = "cookiename", defaultValue = "localhost-idtoken") cookieName: String,
        @RequestParam(value = "redirect", required = false) redirect: String?,
        response: javax.servlet.http.HttpServletResponse,
    ): javax.servlet.http.Cookie? {
        val token: String = mockOAuth2Server.issueToken(
            issuerId,
            MockLoginController::class.java.getSimpleName(),
            DefaultOAuth2TokenCallback(
                issuerId,
                subject,
                JOSEObjectType.JWT.getType(),
                listOf(audience),
                mapOf("acr" to "Level4"),
                3600L,
            ),
        ).serialize()

        return this.createCookieAndAddToResponse(response, cookieName, token, redirect)
    }

    private fun createCookieAndAddToResponse(
        response: javax.servlet.http.HttpServletResponse,
        cookieName: String,
        token: String,
        redirect: String?,
    ): javax.servlet.http.Cookie? {
        val cookie: javax.servlet.http.Cookie = javax.servlet.http.Cookie(cookieName, token)
        cookie.setDomain(".fly.dev/")
        cookie.setPath("/")
        response.addCookie(cookie)
        return if (redirect != null) {
            response.sendRedirect(redirect)
            null
        } else {
            cookie
        }
    }
}
