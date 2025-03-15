package dev.mcarr.la.data.response

import kotlinx.serialization.Serializable

/**
 * Represents a response from this application where the
 * endpoint has exported data and sent it back to the
 * client.
 *
 * @param data The export data
 * */
@Serializable
data class ExportResponse(
    val data: String
)