package dev.mcarr.la.data.request

import kotlinx.serialization.Serializable

/**
 * Interface from which any HTTP request payloads
 * should inherit.
 *
 * This interface exists for the sake of forcing
 * functions to accept data in a specific format.
 *
 * ie. So that a function can accept an arbitrary
 * Serializable object as a payload, which it will
 * then serialize to JSON.
 * */
@Serializable
sealed interface IRequest