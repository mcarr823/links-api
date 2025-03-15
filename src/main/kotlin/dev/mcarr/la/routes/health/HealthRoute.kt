package dev.mcarr.la.routes.health

import dev.mcarr.la.routes.AbstractRoute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * /health endpoint.
 *
 * Used for performing health checks on the application.
 * */
@RestController
@RequestMapping("/health")
class HealthRoute : AbstractRoute() {

    /**
     * POST /health
     *
     * This endpoint should always return a successful response,
     * as long as the service is running and the endpoint is reachable.
     *
     * @return A BooleanResponse object with success set to true
     *
     * @see dev.mcarr.la.data.response.BooleanResponse
     * */
    @PostMapping
    fun healthCheck() = booleanRequest { true }

}