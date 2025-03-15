package dev.mcarr.la.data.request

import dev.mcarr.la.data.enums.ExportFormat
import kotlinx.serialization.Serializable

/**
 * Represents a payload sent to the /export endpoint.
 * */
class ExportRequest {

    /**
     * Read request.
     *
     * All export requests are read requests, since an export
     * is just a download of information from the server.
     *
     * @param format The format in which the data should be
     * downloaded
     *
     * @see ExportFormat
     * */
    @Serializable
    data class Read(
        val format: ExportFormat,
    ) : IRequest

}