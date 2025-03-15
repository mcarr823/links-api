package dev.mcarr.la.routes.imports

import dev.mcarr.la.classes.LinkGroupArray
import dev.mcarr.la.data.enums.ExportFormat
import dev.mcarr.la.data.enums.ImportFormat
import dev.mcarr.la.data.request.ExportRequest
import dev.mcarr.la.data.request.ImportRequest
import dev.mcarr.la.routes.AbstractRoute
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * /import endpoint.
 *
 * Used for importing data into the database.
 * */
@RestController
@RequestMapping("/import")
class ImportRoute : AbstractRoute() {

    /**
     * PUT /import
     *
     * API route for importing data into the database.
     *
     * Imports data in one of the formats defined in the
     * ImportFormat enum.
     *
     * @param req Payload containing the data to be imported,
     * and also describing its format
     *
     * @return An InsertMultiResponse object containing the
     * IDs of the imported LinkGroups
     *
     * @see ImportFormat
     * @see ImportRequest.Create
     * @see dev.mcarr.la.data.response.InsertMultiResponse
     * */
    @PutMapping
    fun importData(
        @RequestBody req: ImportRequest.Create
    ) = insertMultiRequest {

        // Convert the data according to the format specified
        // in the request payload.
        val lga = when (req.format){
            ImportFormat.LINKS ->
                LinkGroupArray.importJson(req.text)
            ImportFormat.ONETAB ->
                LinkGroupArray.importOnetab(req.text)
        }

        // Insert each of the parsed LinkGroup objects
        // into the database and map the results (their IDs).
        lga.groups.map {
            dataSource.insertLinkGroup(it)
        }

    }

}