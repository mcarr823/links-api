package dev.mcarr.la.data.response

import kotlinx.serialization.Serializable

/**
 * Represents a response from the server whereby
 * the operation has resulted in one or more
 * insert functions being run.
 *
 * Each individual insert function will return an ID
 * (the row ID of the newly inserted database entity),
 * and those IDs will be placed in the ids list of the
 * response.
 *
 * @param ids List of integers, where each one
 * corresponds to the result of a different insert request
 * */
@Serializable
data class InsertMultiResponse(
    val ids: List<Int>
)