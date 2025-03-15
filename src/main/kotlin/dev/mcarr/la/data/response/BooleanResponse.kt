package dev.mcarr.la.data.response

import kotlinx.serialization.Serializable

/**
 * Represents a response from this application where the
 * endpoint is expected to return true or false, indicating
 * that a command either succeeded or failed.
 *
 * @param success True if the command succeeded, otherwise
 * false.
 * */
@Serializable
data class BooleanResponse(
    val success: Boolean
)