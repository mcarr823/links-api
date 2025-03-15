package dev.mcarr.la.routes.group.all

import dev.mcarr.la.data.enums.Endpoint
import dev.mcarr.la.routes.AbstractRouteTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class LinkGroupAllRouteTest : AbstractRouteTest() {

    override val endpoint = Endpoint.GROUP_ALL

    // BEGIN READ methods

    @Test
    fun givenNoLinkGroupsExist_whenReadAllMethodIsCalled_thenReturnNoGroups() {

        val groups = readAllGroups()
        assert(groups.isEmpty())

    }

    @Test
    fun givenAtLeastOneLinkGroupExists_whenReadAllMethodIsCalled_thenReturnGroups() {

        // Create at least one
        val groupId = createGroup()
        assert(groupId > 0)

        val groups = readAllGroups()
        assertEquals(1, groups.size)
        assertEquals(groupId, groups[0].id)

    }

    // END READ methods

    // BEGIN DELETE methods

    @Test
    fun givenLinkGroupExists_whenDeleteAllMethodIsCalled_thenLinkGroupIsDeleted() {

        // Create at least one
        val groupId = createGroup()
        assert(groupId > 0)

        // Confirm that it exists
        val groupsBefore = readAllGroups()
        assertEquals(1, groupsBefore.size)

        // Delete all groups
        val deletedSuccessfully = deleteAllGroups()
        assert(deletedSuccessfully)

        // Confirm that the newly created group no longer exists
        val updatedGroups = readAllGroups()
        assert(updatedGroups.isEmpty())

    }

    // END DELETE methods

}