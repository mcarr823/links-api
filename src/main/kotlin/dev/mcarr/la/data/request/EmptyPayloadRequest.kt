package dev.mcarr.la.data.request

import kotlinx.serialization.Serializable

/**
 * Represents an empty payload (HTTP request body)
 * to be submitted.
 *
 * This is used in cases where the HTTP request function
 * expects a payload, but the endpoint doesn't actually
 * need any data in order to perform a request.
 * */
@Serializable
class EmptyPayloadRequest : IRequest