package dev.mcarr.la.routes

import dev.mcarr.la.classes.Link
import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.data.enums.Endpoint
import dev.mcarr.la.data.request.*
import dev.mcarr.la.data.response.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractRouteTest {

    abstract val endpoint: Endpoint

    @LocalServerPort
    private var port: Int = 0

    private val baseUrl = "http://localhost"

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    protected val jsonDecoder = Json{
        ignoreUnknownKeys = true
    }

    /**
     * Runs before each unit test.
     *
     * Deletes all data from the database, so that way the
     * individual tests don't conflict with one another.
     * */
    @BeforeEach
    fun setup(){

        // Commented out before deleting the groups will
        // also delete the links by cascading.
        //deleteAllLinks()

        deleteAllGroups()

    }

    fun generateRandomName(
        length: Int = 32
    ): String {
        val validCharacters = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (0 until length)
            .map { Random.nextInt(0, validCharacters.size).let { validCharacters[it] } }
            .joinToString("")
    }

    private fun testHttpRequest(
        endpoint: Endpoint,
        method: HttpMethod,
        req: IRequest,
        expectedStatus: HttpStatus
    ): String {

        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        val url = "$baseUrl:$port/${endpoint.value}"

        // This code block is a hacky workaround.
        // Using a sealed interface to define a parameter
        // results in an extra key of "type" being added to the
        // encoded JSON, so this code below manually removes that.
        // This logic should be replaced with either a custom
        // serializer, or some other method of defining what types
        // of classes can be passed to this function.
        val dirtyPayload = Json.encodeToString(req)
        val dirtyJson = Json.decodeFromString<JsonObject>(dirtyPayload)
        val objects = dirtyJson.filter { it.key != "type" }
        val json = JsonObject(objects)

        val payload = Json.encodeToString(json)
        println(payload)
        val body = HttpEntity(payload, headers)

        val response = restTemplate.exchange(url, method, body, String::class.java)
        assertEquals(expectedStatus, response.statusCode)
        assertNotNull(response.body)

        return response.body!!
    }

    protected fun testPutRequest(
        endpoint: Endpoint,
        req: IRequest,
        expectedStatus: HttpStatus = HttpStatus.OK
    ) =
        this.testHttpRequest(endpoint, HttpMethod.PUT, req, expectedStatus)

    protected fun testPostRequest(
        endpoint: Endpoint,
        req: IRequest,
        expectedStatus: HttpStatus = HttpStatus.OK
    ) =
        this.testHttpRequest(endpoint, HttpMethod.POST, req, expectedStatus)

    protected fun testPatchRequest(
        endpoint: Endpoint,
        req: IRequest,
        expectedStatus: HttpStatus = HttpStatus.OK
    ) =
        this.testHttpRequest(endpoint, HttpMethod.PATCH, req, expectedStatus)

    protected fun testDeleteRequest(
        endpoint: Endpoint,
        req: IRequest,
        expectedStatus: HttpStatus = HttpStatus.OK
    ) =
        this.testHttpRequest(endpoint, HttpMethod.DELETE, req, expectedStatus)



    fun createGroup(
        name: String = generateRandomName()
    ): Int {
        val req = LinkGroupRequest.Create(name = name)
        val body = testPutRequest(Endpoint.GROUP, req)

        val resp = jsonDecoder.decodeFromString<InsertResponse>(body)
        return resp.id
    }

    fun createLink(
        groupId: Int,
        name: String = generateRandomName(),
        url: String = generateRandomName(),
        favicon: String = generateRandomName()
    ): Int {
        val req = LinkRequest.Create(
            groupId = groupId,
            link = Link(
                name = name,
                url = url,
                favicon = favicon
            )
        )
        val body = testPutRequest(Endpoint.LINK, req)

        val resp = jsonDecoder.decodeFromString<InsertResponse>(body)
        return resp.id
    }

    fun readGroup(groupId: Int): LinkGroup {
        val req = LinkGroupRequest.Read(id = groupId)
        val body = testPostRequest(Endpoint.GROUP, req)
        val resp = jsonDecoder.decodeFromString<LinkGroupResponse>(body)
        return resp.linkGroup
    }

    fun readAllGroups(): List<LinkGroup> {
        val req = EmptyPayloadRequest()
        val body = testPostRequest(Endpoint.GROUP_ALL, req)
        val resp = jsonDecoder.decodeFromString<LinkGroupArrayResponse>(body)
        return resp.results
    }

    fun readLink(linkId: Int): Link {
        val req = LinkRequest.Read(linkId = linkId)
        val body = testPostRequest(Endpoint.LINK, req)
        val resp = jsonDecoder.decodeFromString<LinkResponse>(body)
        return resp.link
    }

    fun readAllLinks(): List<Link> {
        val req = EmptyPayloadRequest()
        val body = testPostRequest(Endpoint.LINK_ALL, req)
        val resp = jsonDecoder.decodeFromString<LinkMultiResponse>(body)
        return resp.links
    }

    fun deleteAllGroups(): Boolean {
        val req = EmptyPayloadRequest()
        val body = testDeleteRequest(Endpoint.GROUP_ALL, req)
        val resp = jsonDecoder.decodeFromString<BooleanResponse>(body)
        return resp.success
    }

    fun deleteAllLinks(): Boolean {
        val req = EmptyPayloadRequest()
        val body = testDeleteRequest(Endpoint.LINK_ALL, req)
        val resp = jsonDecoder.decodeFromString<BooleanResponse>(body)
        return resp.success
    }

}