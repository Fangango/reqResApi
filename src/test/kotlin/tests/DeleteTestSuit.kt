package tests

import helpers.buildRequestURL
import helpers.sendDeleteRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import strikt.api.expect
import strikt.assertions.isEqualTo


@DisplayName("Delete user")
class DeleteTestSuit: BaseSuit() {
    @ParameterizedTest(name = "user ID {0}")
    @CsvSource(
        value = [
            "2,204",
            "999,204",
            "yy,204",
            "'',204"],
        delimiter = ',')
    fun testDelete(userId: String, expectedCode: Int) {
        val deleteUrl = buildRequestURL(method = "users", resource = userId)
        val response = sendDeleteRequest(deleteUrl)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(expectedCode)
        }
    }
}