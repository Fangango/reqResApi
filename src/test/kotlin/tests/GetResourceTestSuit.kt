package tests

import dataClasses.resources.ResResource
import dataClasses.resources.Resource
import helpers.buildRequestURL
import helpers.parseResponseBody
import helpers.sendGetRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo
import strikt.assertions.isNull


@DisplayName("Get resource")
class GetResourceTestSuit: BaseSuit() {
    private val resourceId = 2
    private val name = "fuchsia rose"
    private val year =  2001
    private val color = "#C74375"
    private val pantoneValue = "17-2031"
    private val resource = Resource(resourceId, name, year, color, pantoneValue)

    @Test
    @DisplayName("Resource exists")
    fun testGetResource(){
        val getResourceUrl = buildRequestURL(method = "unknown", resource = resourceId.toString())
        val response = sendGetRequest(getResourceUrl)
        val result = parseResponseBody<ResResource>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.data).describedAs("Resource data is not correct").isEqualTo(resource)
        }

    }

    @Test
    @DisplayName("Resource not found")
    fun testUserNotFound(){
        val getResourceUrl = buildRequestURL(method = "unknown", resource = "23")
        val response = sendGetRequest(getResourceUrl)
        val result = parseResponseBody<ResResource>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(404)
            that(result.data).describedAs("Unexpected data").isNull()
            that(result.support).describedAs("Unexpected data").isNull()
        }
    }
}