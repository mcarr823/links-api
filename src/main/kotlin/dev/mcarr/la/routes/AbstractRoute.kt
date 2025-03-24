package dev.mcarr.la.routes

import dev.mcarr.la.data.response.*
import dev.mcarr.la.data.sources.DatabaseDataSource
import dev.mcarr.la.interfaces.IDataSource
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity

/**
 * Abstract class from which any API routes should inherit.
 *
 * Provides a data source for the routes to query, as well
 * as generic functions for the individual endpoints to
 * call in order to return data in a consistent format.
 * */
abstract class AbstractRoute {

    /**
     * Data source with which API endpoints should interact.
     * */
    val dataSource: IDataSource = DatabaseDataSource()

    /**
     * Convenience function for returning a HTTP error
     * from an endpoint with an exception message.
     *
     * @param e Exception which was thrown by the application.
     * The exception message will be included in the HTTP
     * response, if provided
     *
     * @return HTTP response containing a ErrorResponse object
     * as the body
     *
     * @see ErrorResponse
     * */
    private fun error(e: Exception): ResponseEntity<ErrorResponse> {
        val err = e.message ?: "Request failed"
        val resp = ErrorResponse(err)
        return ResponseEntity.internalServerError().body(resp)
    }

    /**
     * Function for initiating a download of the provided data
     * as a file instead of a HTTP request body.
     *
     * @param name Name of the downloaded file
     * @param data Data to be included in the file
     *
     * @return HTTP response containing the downloaded data
     * as an attachment/file
     * */
    fun <T> download(
        name: String,
        data: T
    ): ResponseEntity<T> {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$name\"")
            .body(data)
    }

    /**
     * Performs a request which should return true or false,
     * then returns the result of that request as a BooleanResponse
     * and transmits it to the HTTP client.
     *
     * @param predicate Operation to run which should return
     * either true or false
     *
     * @return HTTP response containing a BooleanResponse object
     * as the body
     *
     * @see BooleanResponse
     * */
    fun booleanRequest(
        predicate: () -> Boolean
    ): ResponseEntity<*> =
        request{
            val result = predicate()
            BooleanResponse(result)
        }

    /**
     * Performs a request which should return true or false
     * for each of the individual components of the request,
     * then returns the result of that request as a BooleanMultiResponse
     * and transmits it to the HTTP client.
     *
     * @param predicate Operation to run which should return
     * either true or false for each component of the request
     *
     * @return HTTP response containing a BooleanMultiResponse object
     * as the body
     *
     * @see BooleanMultiResponse
     * */
    fun booleanMultiRequest(
        predicate: () -> List<Boolean>
    ): ResponseEntity<*> =
        request{
            val result = predicate()
            BooleanMultiResponse(result)
        }

    /**
     * Performs a request which should return an integer (an ID)
     * as a result of a database insert operation.
     *
     * ie. An operation which returns the row ID of an object which
     * was inserted into the database.
     *
     * The result of that request is then put in a InsertResponse object
     * and transmitted to the HTTP client.
     *
     * @param predicate Operation to run which should return
     * an integer (a database row ID)
     *
     * @return HTTP response containing a InsertResponse object
     * as the body
     *
     * @see InsertResponse
     * */
    fun insertRequest(
        predicate: () -> Int
    ): ResponseEntity<*> =
        request{
            val id = predicate()
            InsertResponse(id = id)
        }

    /**
     * Performs a request which should return a list of integers
     * (IDs) as a result of multiple database insert operations.
     *
     * ie. Operations which return the row IDs of objects which
     * were inserted into the database.
     *
     * The results of those request are then put in a InsertMultiResponse
     * object and transmitted to the HTTP client.
     *
     * @param predicate Operation to run which should return
     * a list of integers (database row IDs)
     *
     * @return HTTP response containing a InsertMultiResponse object
     * as the body
     *
     * @see InsertMultiResponse
     * */
    fun insertMultiRequest(
        predicate: () -> List<Int>
    ): ResponseEntity<*> =
        request{
            val ids = predicate()
            InsertMultiResponse(ids = ids)
        }

    /**
     * Performs a request which should return a string representation
     * of the database's data.
     *
     * eg. A JSON dump of the database.
     *
     * The result of that request is then put in an ExportResponse object
     * and transmitted to the HTTP client.
     *
     * @param predicate Operation to run which should return
     * a string representation of the database's data
     *
     * @return HTTP response containing a ExportResponse object
     * as the body
     *
     * @see ExportResponse
     * */
    fun exportRequest(
        predicate: () -> String
    ): ResponseEntity<*> =
        request {
            val str = predicate()
            ExportResponse(data = str)
        }

    /**
     * Performs a generic request which should return data in
     * a format described by the calling function.
     *
     * This function runs database operations inside of a transaction.
     *
     * If the operation is a success, it returns an OK HTTP 200 response.
     * If not, it returns a HTTP 500 error response.
     *
     * @param predicate Operation to run inside of a transaction.
     * Should return data in a format described by the calling function
     *
     * @return HTTP response entity
     * */
    fun <T> request(
        predicate: Transaction.() -> T
    ): ResponseEntity<*> {
        //Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        try{
            val response = transaction {
                predicate()
            }
            return ResponseEntity.ok(response)
        }catch (e: Exception){
            return error(e)
        }
    }

}