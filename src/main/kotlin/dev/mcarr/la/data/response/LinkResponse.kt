package dev.mcarr.la.data.response

import dev.mcarr.la.classes.Link
import kotlinx.serialization.Serializable

/**
 * Represents a response from this application where the
 * endpoint is expected to return a Link object.
 *
 * @param id ID of the newly inserted database entity
 * @param link The Link object
 * */
@Serializable
data class LinkResponse(
    val id: Int,
    val link: Link
)