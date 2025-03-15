package dev.mcarr.la.data.request

import dev.mcarr.la.classes.LinkGroup
import kotlinx.serialization.Serializable

/**
 * Represents a payload sent to the /group endpoint.
 * */
class LinkGroupRequest {

    /**
     * Request to insert/create a new LinkGroup into
     * the database.
     *
     * @param name Name of the new linkgroup to create.
     * */
    @Serializable
    data class Create(
        val name: String
    ) : IRequest

    /**
     * Request to read a LinkGroup from the database
     * based on its ID.
     *
     * @param id ID of the linkgroup to read.
     * */
    @Serializable
    data class Read(
        val id: Int
    ) : IRequest

    /**
     * Request to update a LinkGroup in the database.
     *
     * @param linkGroup Updated LinkGroup object to
     * overwrite the existing one with the same ID
     * */
    @Serializable
    data class Update(
        val linkGroup: LinkGroup
    ) : IRequest

    /**
     * Request to delete a LinkGroup from the database.
     *
     * @param id ID of the linkgroup to delete
     * */
    @Serializable
    data class Delete(
        val id: Int
    ) : IRequest

}