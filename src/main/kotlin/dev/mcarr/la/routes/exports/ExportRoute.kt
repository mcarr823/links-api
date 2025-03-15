package dev.mcarr.la.routes.exports

import dev.mcarr.la.classes.LinkGroupArray
import dev.mcarr.la.data.daos.LinkDao
import dev.mcarr.la.data.daos.LinkGroupDao
import dev.mcarr.la.data.enums.ExportFormat
import dev.mcarr.la.data.request.ExportRequest
import dev.mcarr.la.routes.AbstractRoute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * /export endpoint.
 *
 * Used for exporting data from the database.
 * */
@RestController
@RequestMapping("/export")
class ExportRoute : AbstractRoute() {

    /**
     * POST /export
     *
     * API route for exporting data from the database.
     *
     * Exports data in one of the formats defined in the
     * ExportFormat enum.
     *
     * @param req Payload describing the format of the data
     * to be exported
     *
     * @return An ExportResponse object containing the export data
     *
     * @see ExportFormat
     * @see ExportRequest.Read
     * @see dev.mcarr.la.data.response.ExportResponse
     * */
    @PostMapping
    fun exportData(
        @RequestBody req: ExportRequest.Read
    ) = exportRequest {
        val groups = LinkGroupDao.getAll().map { g ->
            g.clone(LinkDao.getByGroupId(g.id))
        }
        val arr = LinkGroupArray(groups)
        when(req.format){
            ExportFormat.LINKS -> arr.exportJson()
            ExportFormat.ONETAB -> arr.exportOnetab()
        }

        // TODO implement a file download as well
        //return download("links.json", json)
        //return download("onetab.txt", txt)

    }

}