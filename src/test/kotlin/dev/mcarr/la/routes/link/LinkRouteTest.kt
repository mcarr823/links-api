package dev.mcarr.la.routes.group

import dev.mcarr.la.classes.Link
import dev.mcarr.la.data.enums.Endpoint
import dev.mcarr.la.data.request.EmptyPayloadRequest
import dev.mcarr.la.data.request.LinkRequest
import dev.mcarr.la.data.response.*
import dev.mcarr.la.routes.AbstractRouteTest
import org.junit.jupiter.api.Test
import org.springframework.http.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class LinkRouteTest : AbstractRouteTest() {

    override val endpoint = Endpoint.LINK


    // BEGIN CREATE methods

    @Test
    fun givenParamsAreProvided_whenCreateMethodIsCalled_thenReturnGroupId() {

        val groupId = createGroup()
        assert(groupId >= 1)

        val linkId = createLink(groupId)
        assert(linkId >= 1)

    }

    @Test
    fun givenParamsAreAbsent_whenCreateMethodIsCalled_thenReturnError() {

        val req = EmptyPayloadRequest()
        val body = testPutRequest(endpoint, req, HttpStatus.BAD_REQUEST)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Bad Request", resp.error)

    }

    // END CREATE methods


    // BEGIN READ methods

    @Test
    fun givenLinkExists_whenReadMethodIsCalled_thenReturnLink() {

        // Create first
        val groupId = createGroup()

        val name = generateRandomName()
        val url = generateRandomName()
        val favicon = generateRandomName()
        val linkId = createLink(
            groupId = groupId,
            name = name,
            url = url,
            favicon = favicon
        )

        val link = readLink(linkId)
        assertEquals(name, link.name)
        assertEquals(url, link.url)
        assertEquals(favicon, link.favicon)

    }

    @Test
    fun givenLinkDoesNotExist_whenReadMethodIsCalled_thenReturnError() {

        val linkId = -1
        val req = LinkRequest.Read(linkId = linkId)
        val body = testPostRequest(endpoint, req, HttpStatus.INTERNAL_SERVER_ERROR)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Link not found", resp.error)

    }

    @Test
    fun givenIdIsAbsent_whenReadMethodIsCalled_thenReturnError() {

        val req = EmptyPayloadRequest()
        val body = testPostRequest(endpoint, req, HttpStatus.BAD_REQUEST)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Bad Request", resp.error)

    }

    // END READ methods


    // BEGIN UPDATE methods

    private fun update(
        linkId: Int,
        newName: String,
        newUrl: String,
        newFavicon: String
    ): Boolean {
        val req = LinkRequest.Update(
            linkId = linkId,
            link = Link(
                name = newName,
                url = newUrl,
                favicon = newFavicon
            )
        )
        val body = testPatchRequest(endpoint, req)
        val resp = jsonDecoder.decodeFromString<BooleanResponse>(body)
        return resp.success
    }

    @Test
    fun givenLinkExists_whenUpdateMethodIsCalled_thenReturnSuccess() {

        val groupId = createGroup()
        val linkId = createLink(groupId)
        val oldLink = readLink(linkId)

        val newName = generateRandomName()
        val newUrl = generateRandomName()
        val newFavicon = generateRandomName()

        val success = update(linkId, newName, newUrl, newFavicon)
        assert(success)

        val link = readLink(linkId)
        assertEquals(newName, link.name)
        assertEquals(newUrl, link.url)
        assertEquals(newFavicon, link.favicon)

        assertNotEquals(oldLink.name, link.name)
        assertNotEquals(oldLink.url, link.url)
        assertNotEquals(oldLink.favicon, link.favicon)

    }

    @Test
    fun givenLinkDoesNotExist_whenUpdateMethodIsCalled_thenReturnError() {

        val linkId = -1
        val success = update(linkId, "", "", "")
        assert(!success)

    }

    @Test
    fun givenIdIsAbsent_whenUpdateMethodIsCalled_thenReturnError() {

        val req = EmptyPayloadRequest()
        val body = testPatchRequest(endpoint, req, HttpStatus.BAD_REQUEST)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Bad Request", resp.error)

    }

    // END UPDATE methods


    // BEGIN DELETE methods

    private fun delete(linkId: Int): Boolean {
        val req = LinkRequest.Delete(linkId = linkId)
        val body = testDeleteRequest(endpoint, req)
        val resp = jsonDecoder.decodeFromString<BooleanResponse>(body)
        return resp.success
    }

    @Test
    fun givenLinkExists_whenDeleteMethodIsCalled_thenReturnSuccess() {

        // TODO get full list of links before and after and check
        // if it's actually been removed

        val groupId = createGroup()
        val linkId = createLink(groupId)
        val success = delete(linkId)
        assert(success)

    }

    @Test
    fun givenLinkDoesNotExist_whenDeleteMethodIsCalled_thenReturnError() {

        val linkId = -1
        val success = delete(linkId)
        assert(!success)

    }

    @Test
    fun givenIdIsAbsent_whenDeleteMethodIsCalled_thenReturnError() {

        val req = EmptyPayloadRequest()
        val body = testDeleteRequest(endpoint, req, HttpStatus.BAD_REQUEST)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)

        assertEquals("Bad Request", resp.error)

    }

    // END DELETE methods

}