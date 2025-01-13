package tests

import dataClasses.resources.ResResources
import helpers.buildRequestURL
import helpers.parseResponseBody
import helpers.sendGetRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import testData.*


@DisplayName("Get resources")
class GetResourcesTestSuit: BaseSuit() {

    @Test
    @DisplayName("send request with default params")
    fun testGetResourcesWithoutParams(){
        val getResourcesUrl = buildRequestURL(method = "unknown")
        val response = sendGetRequest(getResourcesUrl)
        val result = parseResponseBody<ResResources>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.page).describedAs("Unexpected page").isEqualTo(1)
            that(result.perPage).describedAs("Unexpected perPage").isEqualTo(6)
            that(result.total).describedAs("Unexpected total").isEqualTo(12)
            that(result.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
        }
        result.data?.forEach { user ->
            expect { that(resourcesWithoutParams).describedAs("Resource with id=${user.id} not in expected list")
                .contains(user) }
        }
    }

    @Test
    @DisplayName("send request with page param")
    fun testGetResourcesWithPageParams(){
        val queryParams = mapOf("page" to "2")
        val getResourcesUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
        val response = sendGetRequest(getResourcesUrl)
        val result = parseResponseBody<ResResources>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.page).describedAs("Unexpected page").isEqualTo(2)
            that(result.perPage).describedAs("Unexpected perPage").isEqualTo(6)
            that(result.total).describedAs("Unexpected total").isEqualTo(12)
            that(result.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
        }
        result.data?.forEach { user ->
            expect { that(resourcesWithPageParam).describedAs("Resource with id=${user.id} not in expected list")
                .contains(user) }
        }
    }

    @Test
    @DisplayName("send request with per_page param")
    fun testGetResourcesWithPerPageParams(){
        val queryParams = mapOf("per_page" to "3")
        val getResourcesUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
        val response = sendGetRequest(getResourcesUrl)
        val result = parseResponseBody<ResResources>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.page).describedAs("Unexpected page").isEqualTo(1)
            that(result.perPage).describedAs("Unexpected perPage").isEqualTo(3)
            that(result.total).describedAs("Unexpected total").isEqualTo(12)
            that(result.totalPages).describedAs("Unexpected totalPages").isEqualTo(4)
        }
        result.data?.forEach { user ->
            expect { that(resourcesWitPerPageParam).describedAs("Resource with id=${user.id} not in expected list")
                .contains(user) }
        }
    }

    @Test
    @DisplayName("send request with page and per_page params")
    fun testGetResourcesWithAllParams(){
        val queryParams = mapOf("page" to "2", "per_page" to "4")
        val getResourcesUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
        val response = sendGetRequest(getResourcesUrl)
        val result = parseResponseBody<ResResources>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.page).describedAs("Unexpected page").isEqualTo(2)
            that(result.perPage).describedAs("Unexpected perPage").isEqualTo(4)
            that(result.total).describedAs("Unexpected total").isEqualTo(12)
            that(result.totalPages).describedAs("Unexpected totalPages").isEqualTo(3)
        }
        result.data?.forEach { user ->
            expect { that(resourcesWitAllParams).describedAs("Resource with id=${user.id} not in expected list")
                .contains(user) }
        }
    }

    @Test
    @DisplayName("send request with incorrect page param")
    fun testGetResourcesWithIncorrectPageParams(){
        val queryParams = mapOf("page" to "20")
        val getResourcesUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
        val response = sendGetRequest(getResourcesUrl)
        val result = parseResponseBody<ResResources>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.page).describedAs("Unexpected page").isEqualTo(20)
            that(result.perPage).describedAs("Unexpected perPage").isEqualTo(6)
            that(result.total).describedAs("Unexpected total").isEqualTo(12)
            that(result.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
            that(result.data?.size).describedAs("Resources list is not empty").isEqualTo(0)
        }
    }

    @Test
    @DisplayName("send request with incorrect per_page param")
    fun testGetResourcesWithBigPerPageParams(){
        val queryParams = mapOf("per_page" to "30")
        val getResourcesUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
        val response = sendGetRequest(getResourcesUrl)
        val result = parseResponseBody<ResResources>(response)
        expect {
            that(response.code).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.page).describedAs("Unexpected page").isEqualTo(1)
            that(result.perPage).describedAs("Unexpected perPage").isEqualTo(30)
            that(result.total).describedAs("Unexpected total").isEqualTo(12)
            that(result.totalPages).describedAs("Unexpected totalPages").isEqualTo(1)
        }
        result.data?.forEach { user ->
            expect { that(allResources).describedAs("Resource with id=${user.id} not in expected list")
                .contains(user) }
        }
    }
}