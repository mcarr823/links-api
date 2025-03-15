package dev.mcarr.la.data.request

import kotlinx.serialization.Serializable

/**
 * For testing purposes only.
 *
 * This class is used to intentionally make invalid
 * import or export payloads by allowing for the format
 * to be a string instead of a ExportFormat/ImportFormat
 * enum value.
 *
 * This is so that we can test what will happen if the
 * server receives an unexpected value from the client.
 *
 * @param String representation of an import or export
 * format
 *
 * @see dev.mcarr.la.data.enums.ExportFormat
 * @see dev.mcarr.la.data.enums.ImportFormat
 * */
@Serializable
data class FormatPayload(
    val format: String
) : IRequest
