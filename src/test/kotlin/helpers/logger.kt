package helpers

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestWatcher


val LOGGER = KotlinLogging.logger { }

class LoggingTestWatcher : TestWatcher {
    override fun testSuccessful(context: ExtensionContext) {
        LOGGER.info { "Test ${context.displayName} was successful" }
    }

    override fun testFailed(context: ExtensionContext, cause: Throwable) {
        LOGGER.error(cause) { "Test ${context.displayName} failed" }
    }
}