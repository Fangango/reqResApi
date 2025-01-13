package tests

import strikt.api.Assertion
import strikt.assertions.isGreaterThanOrEqualTo
import java.time.Duration
import java.time.Instant


fun Assertion.Builder<Instant>.isApproximatelyEqualTo(expected: Instant, tolerance: Duration): Assertion.Builder<Instant> {
    return this.isGreaterThanOrEqualTo(expected.minus(tolerance))
}
