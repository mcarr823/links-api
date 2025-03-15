package dev.mcarr.la.data.response

import kotlinx.serialization.Serializable

/**
 * Represents a response from the server whereby
 * the operation has resulted in one or more
 * pass/fail functions being run.
 *
 * For example, it could be the result of a request
 * to update 5 links at once, in which case this
 * response would contain a boolean value for each
 * of those 5 individual requests to signify if they
 * have passed or failed.
 *
 * @param success List of boolean values, where each
 * one corresponds to a different request sent to
 * the server
 * */
@Serializable
data class BooleanMultiResponse(
    val success: List<Boolean>
)