package dev.mcarr.la.data.response

import dev.mcarr.la.classes.Link
import kotlinx.serialization.Serializable

/**
 * Represents a response from this application where the
 * endpoint is expected to return a list of Link objects.
 *
 * @param links A list of Link objects
 * */
@Serializable
data class LinkMultiResponse(
    val links: List<Link>
)