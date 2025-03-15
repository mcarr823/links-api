package dev.mcarr.la.routes.link

import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.data.daos.LinkDao
import dev.mcarr.la.data.request.LinkGroupRequest
import dev.mcarr.la.data.request.LinkRequest
import dev.mcarr.la.data.response.LinkResponse
import dev.mcarr.la.routes.AbstractRoute
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * /link endpoint.
 *
 * Used for performing CRUD operations on the Link
 * table in the database.
 *
 * @see dev.mcarr.la.data.daos.LinkDao
 * @see dev.mcarr.la.data.tables.LinkTable
 * */
@RestController
@RequestMapping("/link")
class LinkRoute : AbstractRoute() {

    /**
     * PUT /link
     *
     * Inserts a Link into the database.
     *
     * @param req Payload containing the data needed to
     * create a new Link
     *
     * @return An InsertResponse object containing the
     * ID of the inserted Link
     *
     * @see Link
     * @see LinkRequest.Create
     * @see dev.mcarr.la.data.response.InsertResponse
     * */
    @PutMapping
    fun createLink(
        @RequestBody req: LinkRequest.Create
    ) = insertRequest {
        LinkDao.insert(gId = req.groupId, row = req.link)
    }

    /**
     * POST /link
     *
     * Retrieves a Link from the database.
     *
     * @param req Payload containing the ID of the
     * Link to retrieve
     *
     * @return A LinkResponse object containing the
     * Link with the specified ID
     *
     * @throws Exception If the specified Link could
     * not be found
     *
     * @see Link
     * @see LinkRequest.Read
     * @see dev.mcarr.la.data.response.LinkResponse
     * */
    @PostMapping
    fun readLink(
        @RequestBody req: LinkRequest.Read
    ) = request {
        val link = LinkDao.get(linkId = req.linkId) ?: throw Exception("Link not found")
        LinkResponse(id = req.linkId, link = link)
    }

    /**
     * PATCH /link
     *
     * Updates a Link in the database.
     *
     * @param req Payload containing the Link to update
     *
     * @return A BooleanResponse object containing the success
     * status of the update operation
     *
     * @throws Exception If the specified Link could
     * not be updated
     *
     * @see Link
     * @see LinkRequest.Update
     * @see dev.mcarr.la.data.response.BooleanResponse
     * */
    @PatchMapping
    fun updateLink(
        @RequestBody req: LinkRequest.Update
    ) = booleanRequest {
        LinkDao.update(id = req.linkId, row = req.link)
    }

    /**
     * DELETE /link
     *
     * Deletes a Link from the database.
     *
     * @param req Payload containing the ID of the Link
     * to delete
     *
     * @return A BooleanResponse object containing the success
     * status of the delete operation
     *
     * @throws Exception If the specified Link could
     * not be deleted
     *
     * @see Link
     * @see LinkRequest.Delete
     * @see dev.mcarr.la.data.response.BooleanResponse
     * */
    @DeleteMapping
    fun deleteLink(
        @RequestBody req: LinkRequest.Delete
    ) = booleanRequest {
        LinkDao.delete(id = req.linkId)
    }

}