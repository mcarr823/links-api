package dev.mcarr.la.routes.exports

import dev.mcarr.la.classes.Link
import dev.mcarr.la.classes.LinkGroupArray
import dev.mcarr.la.data.enums.Endpoint
import dev.mcarr.la.data.enums.ExportFormat
import dev.mcarr.la.data.request.ExportRequest
import dev.mcarr.la.data.request.FormatPayload
import dev.mcarr.la.data.request.LinkGroupRequest
import dev.mcarr.la.data.request.LinkRequest
import dev.mcarr.la.data.response.ErrorResponse
import dev.mcarr.la.data.response.ExportResponse
import dev.mcarr.la.data.response.InsertResponse
import dev.mcarr.la.routes.AbstractRouteTest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ExportRouteTest : AbstractRouteTest() {

    override val endpoint = Endpoint.EXPORT

    @Test
    fun givenUnknownFormat_whenExportMethodIsCalled_thenExportFails() {

        val payload = FormatPayload("test")
        val body = testPostRequest(endpoint, payload, HttpStatus.BAD_REQUEST)
        val resp = jsonDecoder.decodeFromString<ErrorResponse>(body)
        assertEquals("Bad Request", resp.error)

    }

    @Test
    fun givenLinksFormatIsSpecified_whenExportMethodIsCalled_thenExportIsLinksFormat() {

        val group1Name = generateRandomName()
        val link1Name = generateRandomName()
        val link1Url = generateRandomName()
        val link1Favicon = generateRandomName()
        val link2Name = generateRandomName()
        val link2Url = generateRandomName()
        val link2Favicon = generateRandomName()

        val groupId = createGroup(group1Name)
        createLink(groupId, link1Name, link1Url, link1Favicon)
        createLink(groupId, link2Name, link2Url, link2Favicon)

        val req = ExportRequest.Read(
            format = ExportFormat.LINKS
        )

        val body = testPostRequest(endpoint, req)
        val resp = jsonDecoder.decodeFromString<ExportResponse>(body)
        val data = resp.data
        val lga = LinkGroupArray.importJson(data)
        val groups = lga.groups

        assert(groups.isNotEmpty())

        val group1 = groups.find { it.id == groupId }
        assertNotNull(group1)

        assertEquals(group1Name, group1.name)
        assertEquals(2, group1.links.size)

        val link1 = group1.links[0]
        assertEquals(link1Name, link1.name)
        assertEquals(link1Url, link1.url)
        assertEquals(link1Favicon, link1.favicon)

        val link2 = group1.links[1]
        assertEquals(link2Name, link2.name)
        assertEquals(link2Url, link2.url)
        assertEquals(link2Favicon, link2.favicon)

    }

    @Test
    fun givenOnetabFormatIsSpecified_whenExportMethodIsCalled_thenExportIsOnetabFormat() {

        val group1Name = generateRandomName()
        val link1Name = generateRandomName()
        val link1Url = generateRandomName()
        val link1Favicon = generateRandomName()
        val link2Name = generateRandomName()
        val link2Url = generateRandomName()
        val link2Favicon = generateRandomName()

        val groupId = createGroup(group1Name)
        createLink(groupId, link1Name, link1Url, link1Favicon)
        createLink(groupId, link2Name, link2Url, link2Favicon)

        val req = ExportRequest.Read(
            format = ExportFormat.ONETAB
        )

        val body = testPostRequest(endpoint, req)
        val resp = jsonDecoder.decodeFromString<ExportResponse>(body)
        val data = resp.data
        val lga = LinkGroupArray.importOnetab(data)
        val groups = lga.groups

        assert(groups.isNotEmpty())

        // Onetab tab groups don't have IDs, so we'll need
        // to get creative...
        val group1 = groups.find {
            it.links.size == 2 &&
            it.links[0].name == link1Name &&
            it.links[0].url == link1Url &&
            it.links[1].name == link2Name &&
            it.links[1].url == link2Url
        }
        assertNotNull(group1)

        assertEquals("", group1.name)
        assertEquals(2, group1.links.size)

        val link1 = group1.links[0]
        assertEquals(link1Name, link1.name)
        assertEquals(link1Url, link1.url)
        assertEquals("", link1.favicon)

        val link2 = group1.links[1]
        assertEquals(link2Name, link2.name)
        assertEquals(link2Url, link2.url)
        assertEquals("", link2.favicon)

    }

}