package tests

import dataClasses.users.ResUser
import dataClasses.users.User
import helpers.buildRequestURL
import helpers.parseResponseBody
import helpers.sendGetRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo
import strikt.assertions.isNull


@DisplayName("Get User")
class GetUserTestSuit: BaseSuit() {
    private val userId = 3
    private val email = "emma.wong@reqres.in"
    private val firstName = "Emma"
    private val lastName = "Wong"
    private val avatar = "https://reqres.in/img/faces/3-image.jpg"
    private val user = User(id = userId, email = email, firstName = firstName, lastName = lastName, avatar = avatar)

    @Test
    @DisplayName("User exists")
    fun testGetUser(){
        val getUserUrl = buildRequestURL(method = "users", resource = userId.toString())
        val response = sendGetRequest(getUserUrl)
        val result = parseResponseBody<ResUser>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.data).describedAs("User data is not correct").isEqualTo(user)
        }
    }

    @Test
    @DisplayName("User not found")
    fun testUserNotFound(){
        val getUserUrl = buildRequestURL(method = "users", resource = "22")
        val response = sendGetRequest(getUserUrl)
        val result = parseResponseBody<ResUser>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(404)
            that(result.data).describedAs("Unexpected data").isNull()
            that(result.support).describedAs("Unexpected data").isNull()
        }
    }
}