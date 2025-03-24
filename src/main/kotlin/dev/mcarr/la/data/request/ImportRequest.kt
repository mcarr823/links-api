package dev.mcarr.la.data.request

import dev.mcarr.la.data.enums.ImportFormat
import kotlinx.serialization.Serializable

/**
 * Represents a payload sent to the /import endpoint.
 * */
class ImportRequest {

    /**
     * Insert request.
     *
     * All import requests are insert requests, since an import
     * is an upload of information to the server.
     *
     * @param format The format of the import data
     * @param text The import data
     *
     * @see ImportFormat
     * */
    @Serializable
    data class Create(
        val format: ImportFormat,
        val text: String
    ) : IRequest

}