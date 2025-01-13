package tests

import dataClasses.update.ReqUpdate
import dataClasses.update.ResUpdate
import helpers.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo
import java.time.Duration
import java.time.Instant


@DisplayName("Update user info")
class UpdateTestSuit: BaseSuit() {
    private val name = "morpheus"
    private val job = "zion resident"
    private val data = ReqUpdate(name = name, job = job)
    private val updateUrl = buildRequestURL(method = "users", resource = "2")

    @Test
    @DisplayName("Update user info with PUT method")
    fun testPutUpdate(){
        val currentTime = Instant.now()
        val duration = Duration.ofMillis(3000)
        val response = sendPutRequest(
            url = updateUrl,
            body = buildRequestBody(data)
        )
        val respCode = response.code
        val result = parseResponseBody<ResUpdate>(response)
        expect {
            that(respCode).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.name).describedAs("Unexpected name").isEqualTo(name)
            that(result.job).describedAs("Unexpected name").isEqualTo(job)
            that(Instant.parse(result.updatedAt)).describedAs("updatedAt is not correct")
                .isApproximatelyEqualTo(currentTime, duration)
        }
    }

    @Test
    @DisplayName("Update user info with PATCH method")
    fun testPatchUpdate(){
        val currentTime = Instant.now()
        val duration = Duration.ofMillis(3000)
        val response = sendPatchRequest(
            url = updateUrl,
            body = buildRequestBody(data)
        )
        val respCode = response.code
        val result = parseResponseBody<ResUpdate>(response)
        expect {
            that(respCode).describedAs("response code doesn't equal to expect code").isEqualTo(200)
            that(result.name).describedAs("Unexpected name").isEqualTo(name)
            that(result.job).describedAs("Unexpected name").isEqualTo(job)
            that(Instant.parse(result.updatedAt)).describedAs("updatedAt is not correct")
                .isApproximatelyEqualTo(currentTime, duration)
        }
    }
}