package dev.cocot3ro.smartac

import io.ktor.server.testing.testApplication
import kotlin.test.Test

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
    }
}