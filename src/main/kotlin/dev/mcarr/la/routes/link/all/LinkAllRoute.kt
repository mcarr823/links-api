package dev.mcarr.la.routes.link.all

import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.data.daos.LinkDao
import dev.mcarr.la.data.request.LinkRequest
import dev.mcarr.la.data.response.LinkMultiResponse
import dev.mcarr.la.data.response.LinkResponse
import dev.mcarr.la.routes.AbstractRoute
import org.springframework.web.bind.annotation.*

/**
 * /link/all endpoint.
 *
 * Used for performing operations on all Link table
 * rows in the database simultaneously.
 *
 * @see dev.mcarr.la.data.daos.LinkDao
 * @see dev.mcarr.la.data.tables.LinkTable
 * */
@RestController
@RequestMapping("/link/all")
class LinkAllRoute : AbstractRoute() {

    /**
     * POST /link/all
     *
     * Retrieves all Links from the database.
     *
     * @return A LinkMultiResponse object containing
     * all of the links
     *
     * @see Link
     * @see dev.mcarr.la.data.response.LinkMultiResponse
     * */
    @PostMapping
    fun readLinks() = request {
        val links = LinkDao.getAll()
        LinkMultiResponse(links)
    }

    /**
     * DELETE /link/all
     *
     * Deletes all Links from the database.
     *
     * @return A BooleanResponse object containing the success
     * status of the delete operation
     *
     * @see Link
     * @see dev.mcarr.la.data.response.BooleanResponse
     * */
    @DeleteMapping
    fun deleteLinks() = booleanRequest {
        val numDeleted = LinkDao.deleteAll()
        numDeleted > 0
    }

}