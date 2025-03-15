package dev.mcarr.la.data.response

import kotlinx.serialization.Serializable

/**
 * Represents a response from this application where the
 * endpoint has returned an error.
 *
 * @param error Description of the error which occurred
 * */
@Serializable
data class ErrorResponse(
    val error: String
)