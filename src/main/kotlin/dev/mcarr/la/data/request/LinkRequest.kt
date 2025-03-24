package dev.mcarr.la.data.request

import dev.mcarr.la.classes.Link
import kotlinx.serialization.Serializable

/**
 * Represents a payload sent to the /link endpoint.
 * */
class LinkRequest {

    /**
     * Request to insert/create a new Link into
     * the database.
     *
     * @param groupId ID of the group to which this new
     * Link should be added
     * @param link The new Link to insert into the database
     * */
    @Serializable
    data class Create(
        val groupId: Int,
        val link: Link
    ) : IRequest

    /**
     * Request to read a Link from the database
     * based on its ID.
     *
     * @param linkId ID of the link to read.
     * */
    @Serializable
    data class Read(
        val linkId: Int
    ) : IRequest

    /**
     * Request to update a Link in the database.
     *
     * @param linkId ID of the link to update
     * @param link Updated Link object to overwrite
     * the existing one
     * */
    @Serializable
    data class Update(
        val linkId: Int,
        val link: Link
    ) : IRequest

    /**
     * Request to delete a Link from the database.
     *
     * @param linkId ID of the link to delete
     * */
    @Serializable
    data class Delete(
        val linkId: Int
    ) : IRequest

}