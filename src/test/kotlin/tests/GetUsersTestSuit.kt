package tests

import dataClasses.users.ResUsers
import helpers.buildRequestURL
import helpers.parseResponseBody
import helpers.sendGetRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import testData.userWithPageParam
import testData.usersWithoutParams

@DisplayName("Get users")
class GetUsersTestSuit: BaseSuit() {

    @Test
    @DisplayName("Send request with default params")
    fun testGetUsersWithoutParams() {
        val getUsersUrl = buildRequestURL(method = "users")
        val response = sendGetRequest(getUsersUrl)
        val result = parseResponseBody<ResUsers>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.page).describedAs("Unexpected page").isEqualTo(1)
            that(result.perPage).describedAs("Unexpected perPage").isEqualTo(6)
            that(result.total).describedAs("Unexpected total").isEqualTo(12)
            that(result.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
        }
        result.data?.forEach { user ->
            expect {
                that(usersWithoutParams).describedAs("User with id=${user.id} not in expected list")
                    .contains(user)
            }
        }
    }

    @Test
    @DisplayName("Send request with page param")
    fun testGetUsersWithPageParams() {
        val queryParams = mapOf("page" to "2")
        val getUsersUrl = buildRequestURL(method = "users", queryParam = queryParams)
        val response = sendGetRequest(getUsersUrl)
        val result = parseResponseBody<ResUsers>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.page).describedAs("Unexpected page").isEqualTo(2)
            that(result.perPage).describedAs("Unexpected perPage").isEqualTo(6)
            that(result.total).describedAs("Unexpected total").isEqualTo(12)
            that(result.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
        }
        result.data?.forEach { user ->
            expect {
                that(userWithPageParam).describedAs("User with id=${user.id} not in expected list")
                    .contains(user)
            }
        }
    }
}