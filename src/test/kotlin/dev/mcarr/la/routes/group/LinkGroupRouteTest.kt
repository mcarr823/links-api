package dev.mcarr.la.routes.group

import dev.mcarr.la.classes.Link
import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.data.enums.Endpoint
import dev.mcarr.la.data.request.EmptyPayloadRequest
import dev.mcarr.la.data.request.LinkGroupRequest
import dev.mcarr.la.data.response.*
import dev.mcarr.la.routes.AbstractRouteTest
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class LinkGroupRouteTest : AbstractRouteTest() {

    override val endpoint = Endpoint.GROUP


    // BEGIN CREATE methods

    @Test
    fun givenNameIsProvided_whenCreateMethodIsCalled_thenReturnGroupId() {

        val groupId = createGroup()
        assert(groupId >= 1)

    }

    @Test
    fun givenNameIsAbsent_whenCreateMethodIsCalled_thenReturnError() {

        val payload = EmptyPayloadRequest()
        val body = testPutRequest(endpoint, payload, HttpStatus.BAD_REQUEST)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Bad Request", resp.error)

    }

    // END CREATE methods


    // BEGIN READ methods

    @Test
    fun givenLinkGroupExists_whenReadMethodIsCalled_thenReturnGroup() {

        // Create first
        val name = generateRandomName()
        val groupId = createGroup(name)

        val group = readGroup(groupId)
        assertEquals(groupId, group.id)
        assertEquals(0, group.links.size)
        assertEquals(name, group.name)

    }

    @Test
    fun givenLinkGroupDoesNotExist_whenReadMethodIsCalled_thenReturnError() {

        val groupId = -1
        val req = LinkGroupRequest.Read(id = groupId)

        val body = testPostRequest(endpoint, req, HttpStatus.INTERNAL_SERVER_ERROR)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Link group not found", resp.error)

    }

    @Test
    fun givenIdIsAbsent_whenReadMethodIsCalled_thenReturnError() {

        val payload = EmptyPayloadRequest()
        val body = testPostRequest(endpoint, payload, HttpStatus.BAD_REQUEST)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Bad Request", resp.error)

    }

    // END READ methods


    // BEGIN UPDATE methods

    private fun update(
        groupId: Int,
        newName: String,
        newLinks: List<Link>
    ): Boolean {
        val req = LinkGroupRequest.Update(
            linkGroup = LinkGroup(
                id = groupId,
                name = newName,
                links = newLinks
            )
        )
        val body = testPatchRequest(endpoint, req)
        val resp = jsonDecoder.decodeFromString<BooleanResponse>(body)
        return resp.success
    }

    @Test
    fun givenLinkGroupExists_whenUpdateMethodIsCalled_thenReturnSuccess() {

        val name = generateRandomName()
        val newName = generateRandomName()
        assertNotEquals(name, newName)

        val groupId = createGroup(name)
        val linkName = generateRandomName()
        val linkUrl = generateRandomName()
        val linkFavicon = generateRandomName()
        val newLinks = listOf(
            Link(name = linkName, url = linkUrl, favicon = linkFavicon)
        )

        val success = update(groupId, newName, newLinks)
        assert(success)

        val group = readGroup(groupId)
        assertEquals(groupId, group.id)
        assertEquals(newName, group.name)
        assertEquals(newLinks.size, group.links.size)
        assertEquals(newLinks[0].name, linkName)
        assertEquals(newLinks[0].url, linkUrl)
        assertEquals(newLinks[0].favicon, linkFavicon)

    }

    @Test
    fun givenLinkGroupDoesNotExist_whenUpdateMethodIsCalled_thenReturnError() {

        val groupId = -1
        val success = update(groupId, "", listOf())
        assert(!success)

    }

    @Test
    fun givenIdIsAbsent_whenUpdateMethodIsCalled_thenReturnError() {

        val payload = EmptyPayloadRequest()
        val body = testPatchRequest(endpoint, payload, HttpStatus.BAD_REQUEST)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Bad Request", resp.error)

    }

    // END UPDATE methods


    // BEGIN DELETE methods

    private fun delete(groupId: Int): Boolean {
        val req = LinkGroupRequest.Delete(id = groupId)
        val body = testDeleteRequest(endpoint, req)
        val resp = jsonDecoder.decodeFromString<BooleanResponse>(body)
        return resp.success
    }

    @Test
    fun givenLinkGroupExists_whenDeleteMethodIsCalled_thenReturnSuccess() {

        val groupId = createGroup()
        val linkId = createLink(groupId)

        val updatedList = readAllGroups()
        assertEquals(1, updatedList.size)
        assertEquals(1, updatedList[0].links.size)

        val success = delete(groupId)
        assert(success)

        val finalListOfGroups = readAllGroups()
        assertEquals(0, finalListOfGroups.size)

        val finalListOfLinks = readAllLinks()
        assertEquals(0, finalListOfLinks.size)

    }

    @Test
    fun givenLinkGroupDoesNotExist_whenDeleteMethodIsCalled_thenReturnError() {

        val groupId = -1
        val success = delete(groupId)
        assert(!success)

    }

    @Test
    fun givenIdIsAbsent_whenDeleteMethodIsCalled_thenReturnError() {

        val payload = EmptyPayloadRequest()
        val body = testDeleteRequest(endpoint, payload, HttpStatus.BAD_REQUEST)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Bad Request", resp.error)

    }

    // END DELETE methods

}