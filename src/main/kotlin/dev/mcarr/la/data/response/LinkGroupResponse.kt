package dev.mcarr.la.data.response

import dev.mcarr.la.classes.LinkGroup
import kotlinx.serialization.Serializable

/**
 * Represents a response from this application where the
 * endpoint is expected to return a LinkGroup object.
 *
 * @param linkGroup The received LinkGroup object
 * */
@Serializable
data class LinkGroupResponse(
    val linkGroup: LinkGroup
)