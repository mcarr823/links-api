package dev.mcarr.la.routes.imports

import dev.mcarr.la.classes.Link
import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.classes.LinkGroupArray
import dev.mcarr.la.data.enums.Endpoint
import dev.mcarr.la.data.enums.ImportFormat
import dev.mcarr.la.data.request.ImportRequest
import dev.mcarr.la.data.request.LinkGroupRequest
import dev.mcarr.la.data.response.InsertMultiResponse
import dev.mcarr.la.data.response.LinkGroupResponse
import dev.mcarr.la.routes.AbstractRouteTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ImportRouteTest : AbstractRouteTest() {

    override val endpoint = Endpoint.IMPORT

    @Test
    fun givenDataIsValid_whenImportLinksMethodIsCalled_thenImportIsSuccessful() {

        val group1Name = generateRandomName()
        val link1Name = generateRandomName()
        val link1Url = generateRandomName()
        val link1Favicon = generateRandomName()
        val link2Name = generateRandomName()
        val link2Url = generateRandomName()
        val link2Favicon = generateRandomName()

        val group2Name = generateRandomName()
        val link3Name = generateRandomName()
        val link3Url = generateRandomName()
        val link3Favicon = generateRandomName()

        val lga = LinkGroupArray(listOf(
            LinkGroup(0, group1Name, listOf(
                Link(link1Name, link1Url, link1Favicon),
                Link(link2Name, link2Url, link2Favicon)
            )),
            LinkGroup(0, group2Name, listOf(
                Link(link3Name, link3Url, link3Favicon)
            )),
        ))

        val req = ImportRequest.Create(
            format = ImportFormat.LINKS,
            text = lga.exportJson()
        )
        val body = testPutRequest(endpoint, req)
        val resp = jsonDecoder.decodeFromString<InsertMultiResponse>(body)

        assertEquals(2, resp.ids.size)

        val group1 = readGroup(resp.ids[0])
        assertEquals(group1Name, group1.name)
        assertEquals(2, group1.links.size)

        val group2 = readGroup(resp.ids[1])
        assertEquals(group2Name, group2.name)
        assertEquals(1, group2.links.size)

        val link1 = group1.links[0]
        assertEquals(link1Name, link1.name)
        assertEquals(link1Url, link1.url)
        assertEquals(link1Favicon, link1.favicon)

        val link2 = group1.links[1]
        assertEquals(link2Name, link2.name)
        assertEquals(link2Url, link2.url)
        assertEquals(link2Favicon, link2.favicon)

        val link3 = group2.links[0]
        assertEquals(link3Name, link3.name)
        assertEquals(link3Url, link3.url)
        assertEquals(link3Favicon, link3.favicon)

    }

    @Test
    fun givenDataIsValid_whenImportOnetabMethodIsCalled_thenImportIsSuccessful() {

        val link1Name = generateRandomName()
        val link1Url = generateRandomName()
        val link2Name = generateRandomName()
        val link2Url = generateRandomName()
        val link3Name = generateRandomName()
        val link3Url = generateRandomName()

        val lga = LinkGroupArray(listOf(
            LinkGroup(0, "", listOf(
                Link(link1Name, link1Url, ""),
                Link(link2Name, link2Url, "")
            )),
            LinkGroup(0, "", listOf(
                Link(link3Name, link3Url, "")
            )),
        ))

        val req = ImportRequest.Create(
            format = ImportFormat.ONETAB,
            text = lga.exportOnetab()
        )

        val body = testPutRequest(endpoint, req)
        val resp = jsonDecoder.decodeFromString<InsertMultiResponse>(body)

        assertEquals(2, resp.ids.size)

        val group1 = readGroup(resp.ids[0])
        assertEquals("", group1.name)
        assertEquals(2, group1.links.size)

        val group2 = readGroup(resp.ids[1])
        assertEquals("", group2.name)
        assertEquals(1, group2.links.size)

        val link1 = group1.links[0]
        assertEquals(link1Name, link1.name)
        assertEquals(link1Url, link1.url)
        assertEquals("", link1.favicon)

        val link2 = group1.links[1]
        assertEquals(link2Name, link2.name)
        assertEquals(link2Url, link2.url)
        assertEquals("", link2.favicon)

        val link3 = group2.links[0]
        assertEquals(link3Name, link3.name)
        assertEquals(link3Url, link3.url)
        assertEquals("", link3.favicon)

    }

}