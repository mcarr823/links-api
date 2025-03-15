package dev.mcarr.la.routes.link.all

import dev.mcarr.la.data.enums.Endpoint
import dev.mcarr.la.routes.AbstractRouteTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LinkAllRouteTest : AbstractRouteTest() {

    override val endpoint = Endpoint.LINK_ALL

    // BEGIN READ methods

    @Test
    fun givenNoLinksExist_whenReadAllMethodIsCalled_thenReturnNoLinks() {

        val links = readAllLinks()
        assert(links.isEmpty())

    }

    @Test
    fun givenAtLeastOneLinkExists_whenReadAllMethodIsCalled_thenReturnLinks() {

        // Create group
        val groupId = createGroup()
        assert(groupId > 0)

        // Create link
        val linkId = createLink(groupId)
        assert(linkId > 0)

        // Confirm that the link is in the output
        val links = readAllLinks()
        assert(links.isNotEmpty())

    }

    // END READ methods

    // BEGIN DELETE methods

    @Test
    fun givenLinkExists_whenDeleteAllMethodIsCalled_thenLinkIsDeleted() {

        // Create group
        val groupId = createGroup()
        assert(groupId > 0)

        // Create link
        val linkId = createLink(groupId)
        assert(linkId > 0)

        // Confirm that there's at least one link in the database
        val links = readAllLinks()
        assert(links.isNotEmpty())

        // Delete all links
        val deletedSuccessfully = deleteAllLinks()
        assert(deletedSuccessfully)

        // Confirm that the newly created link no longer exists
        val updatedLinks = readAllLinks()
        assert(updatedLinks.isEmpty())

    }

    // END DELETE methods

}