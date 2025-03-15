package dev.mcarr.la.data.response

import kotlinx.serialization.Serializable

/**
 * Represents a response from this application where the
 * endpoint is expected to return an integer representing
 * the ID of a new database row.
 *
 * @param id ID of the newly inserted database entity
 * */
@Serializable
data class InsertResponse(
    val id: Int
)