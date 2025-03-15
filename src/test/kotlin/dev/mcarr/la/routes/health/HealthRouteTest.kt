package dev.mcarr.la.routes.health

import dev.mcarr.la.data.enums.Endpoint
import dev.mcarr.la.data.request.EmptyPayloadRequest
import dev.mcarr.la.data.response.BooleanResponse
import dev.mcarr.la.data.response.InsertMultiResponse
import dev.mcarr.la.routes.AbstractRouteTest
import org.junit.jupiter.api.Test

class HealthRouteTest : AbstractRouteTest() {

    override val endpoint = Endpoint.HEALTH

    @Test
    fun givenServerIsRunning_whenHealthCheckIsPerformed_thenRequestIsSuccessful() {

        val req = EmptyPayloadRequest()
        val body = testPostRequest(endpoint, req)
        val resp = jsonDecoder.decodeFromString<BooleanResponse>(body)

        assert(resp.success)

    }

}