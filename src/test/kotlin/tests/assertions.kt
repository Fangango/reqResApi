package tests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import strikt.api.Assertion
import strikt.api.expect
import strikt.api.expectCatching
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThanOrEqualTo
import strikt.assertions.isLessThanOrEqualTo
import strikt.assertions.isSuccess
import java.time.Duration
import java.time.Instant


fun <T> checkBodyIsValid(respBody: String, clazz: Class<T>){
    expectCatching {
        jacksonObjectMapper().readValue(respBody, clazz)
    }.describedAs("response body is invalid").isSuccess()
}

fun checkResponseCodeEqualTo(expectCode: Int, responseCode: Int) {
    expect {
        that(responseCode).describedAs("response code doesn't equal to expect code").isEqualTo(expectCode)
    }
}

fun Assertion.Builder<Instant>.isApproximatelyEqualTo(expected: Instant, tolerance: Duration): Assertion.Builder<Instant> {
    return this.isGreaterThanOrEqualTo(expected.minus(tolerance)).isLessThanOrEqualTo(expected.plus(tolerance))
}
