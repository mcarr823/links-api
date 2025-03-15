package dev.mcarr.la.data.response

import dev.mcarr.la.classes.LinkGroup
import kotlinx.serialization.Serializable

/**
 * Represents a response from this application where the
 * endpoint is expected to return a list of LinkGroup objects.
 *
 * @param results List of LinkGroup objects
 * */
@Serializable
data class LinkGroupArrayResponse(
    val results: List<LinkGroup>
)