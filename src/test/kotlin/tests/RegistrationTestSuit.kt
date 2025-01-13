package tests

import dataClasses.register.ErrorRegister
import dataClasses.register.ReqRegister
import dataClasses.register.ResRegister
import helpers.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import strikt.api.expect
import strikt.assertions.isEqualTo


@DisplayName("Registration")
class RegistrationTestSuit: BaseSuit() {

    private val registraionURL = buildRequestURL(method = "register")

    @Test
    @DisplayName("Define user register test")
    fun testDefineUserRegister() {
        //LOGGER.info { "Register. Start test: define user register test" }
        val response = sendPostRequest(
            url = registraionURL,
            body = buildRequestBody(ReqRegister(email = "eve.holt@reqres.in", password = "pistol"))
        )
        val respCode = response.code
        val result = parseResponseBody<ResRegister>(response)
        expect {
            that(respCode).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.id).describedAs("id is not equal to 4").isEqualTo(4)
            that(result.token).describedAs("token id not correct").isEqualTo("QpwL5tke4Pnpja7X4")
        }
    }

    @ParameterizedTest(name = "email= {0}, password= {1}, expectation= {2}")
    @CsvSource(
        value = [
            "eve.holt@reqres.in,,Missing password",
            ",pistol,Missing email or username",
            ",,Missing email or username",
            "eve1.holt@reqres.in,pistol,Note: Only defined users succeed registration"],
        delimiter = ','
    )
    fun testRegisterErrors(email: String?, password: String?, expectation: String) {
        //LOGGER.info { "Register. Start test: ${testInfo.displayName}" }
        val response = sendPostRequest(
            url = registraionURL,
            body = buildRequestBody(ReqRegister(email = email, password = password))
        )
        val respCode = response.code
        val error = parseResponseBody<ErrorRegister>(response)
        expect {
            that(respCode).describedAs("response code doesn't equal to expect code").isEqualTo(400)
            that(error.error).describedAs("Unexpected error message").isEqualTo(expectation)
        }
    }
}