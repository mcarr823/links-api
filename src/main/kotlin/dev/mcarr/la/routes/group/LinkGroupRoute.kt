package dev.mcarr.la.routes.group

import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.data.request.LinkGroupRequest
import dev.mcarr.la.data.response.LinkGroupResponse
import dev.mcarr.la.routes.AbstractRoute
import org.springframework.web.bind.annotation.*

/**
 * /group endpoint.
 *
 * Used for performing CRUD operations on the LinkGroup
 * table in the database.
 *
 * @see dev.mcarr.la.data.daos.LinkGroupDao
 * @see dev.mcarr.la.data.tables.LinkGroupTable
 * */
@RestController
@RequestMapping("/group")
class LinkGroupRoute : AbstractRoute() {

    /**
     * PUT /group
     *
     * Inserts a LinkGroup into the database.
     *
     * @param req Payload containing the data needed to
     * create a new LinkGroup
     *
     * @return An InsertResponse object containing the
     * ID of the inserted LinkGroup
     *
     * @see LinkGroupRequest.Create
     * @see dev.mcarr.la.data.response.InsertResponse
     * */
    @PutMapping
    fun createLinkGroup(
        @RequestBody req: LinkGroupRequest.Create
    ) = insertRequest {
        val linkGroup = LinkGroup(0, req.name, listOf())
        dataSource.insertLinkGroup(linkGroup)
    }

    /**
     * POST /group
     *
     * Retrieves a LinkGroup from the database.
     *
     * @param req Payload containing the ID of the
     * LinkGroup to retrieve
     *
     * @return A LinkGroupResponse object containing the
     * LinkGroup with the specified ID
     *
     * @throws Exception If the specified LinkGroup could
     * not be found
     *
     * @see LinkGroupRequest.Read
     * @see dev.mcarr.la.data.response.LinkGroupResponse
     * */
    @PostMapping
    fun readLinkGroup(
        @RequestBody req: LinkGroupRequest.Read
    ) = request {
        val linkGroup = dataSource.getLinkGroup(req.id) ?: throw Exception("Link group not found")
        LinkGroupResponse(linkGroup)
    }

    /**
     * PATCH /group
     *
     * Updates a LinkGroup in the database.
     *
     * @param req Payload containing the LinkGroup to update
     *
     * @return A BooleanResponse object containing the success
     * status of the update operation
     *
     * @throws Exception If the specified LinkGroup could
     * not be updated
     *
     * @see LinkGroupRequest.Update
     * @see dev.mcarr.la.data.response.BooleanResponse
     * */
    @PatchMapping
    fun updateLinkGroup(
        @RequestBody req: LinkGroupRequest.Update
    ) = booleanRequest {
        dataSource.updateLinkGroup(req.linkGroup)
    }

    /**
     * DELETE /group
     *
     * Deletes a LinkGroup from the database.
     *
     * @param req Payload containing the ID of the LinkGroup
     * to delete
     *
     * @return A BooleanResponse object containing the success
     * status of the delete operation
     *
     * @throws Exception If the specified LinkGroup could
     * not be deleted
     *
     * @see LinkGroupRequest.Delete
     * @see dev.mcarr.la.data.response.BooleanResponse
     * */
    @DeleteMapping
    fun deleteLinkGroup(
        @RequestBody req: LinkGroupRequest.Delete
    ) = booleanRequest {
        dataSource.deleteLinkGroup(req.id)
    }

}