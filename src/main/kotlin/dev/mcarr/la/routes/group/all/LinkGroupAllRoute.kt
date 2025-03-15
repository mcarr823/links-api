package dev.mcarr.la.routes.group.all

import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.data.response.LinkGroupArrayResponse
import dev.mcarr.la.routes.AbstractRoute
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * /group/all endpoint.
 *
 * Used for performing operations on all LinkGroup table
 * rows in the database simultaneously.
 *
 * @see dev.mcarr.la.data.daos.LinkGroupDao
 * @see dev.mcarr.la.data.tables.LinkGroupTable
 * */
@RestController
@RequestMapping("/group/all")
class LinkGroupAllRoute : AbstractRoute() {

    /**
     * POST /group/all
     *
     * Retrieves all LinkGroups from the database.
     *
     * @return A LinkGroupArrayResponse object containing
     * all of the link groups
     *
     * @see LinkGroup
     * @see dev.mcarr.la.data.response.LinkGroupArrayResponse
     * */
    @PostMapping
    fun readLinkGroups() = request {
        val linkGroups = dataSource.getLinkGroups()
        LinkGroupArrayResponse(linkGroups)
    }

    /**
     * DELETE /group/all
     *
     * Deletes all LinkGroups from the database.
     *
     * @return A BooleanResponse object containing the success
     * status of the delete operation
     *
     * @see LinkGroup
     * @see dev.mcarr.la.data.response.BooleanResponse
     * */
    @DeleteMapping
    fun deleteLinkGroups() = booleanRequest {
        val numDeleted = dataSource.deleteLinkGroups()
        numDeleted > 0
    }

}