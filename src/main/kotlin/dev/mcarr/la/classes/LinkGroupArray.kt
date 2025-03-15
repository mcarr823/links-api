package dev.mcarr.la.classes

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * A container class holding an array of LinkGroup objects.
 *
 * This class exists for the purpose of performing functions on multiple
 * LinkGroup objects at once from a single class.
 *
 * eg. Instead of looping through an array and performing a function on
 * each object, you would put those objects into this class and then
 * call a function on this class.
 *
 * It is also used when transmitting multiple link groups at once.
 * eg. When requesting multiple LinkGroup objects at once from an API
 * query and transmitting them all.
 *
 * @param groups A list of LinkGroup objects to store within this class
 *
 * @see LinkGroup
 */
@Serializable
class LinkGroupArray(
    val groups: List<LinkGroup>
){

    /**
     * Exports each LinkGroup in Onetab's format, then puts them all
     * in a double newline-delimited string.
     *
     * @return Double newline-delimited string of LinkGroup objects
     * in the Onetab format
     */
    fun exportOnetab(): String =
        groups
            .map(LinkGroup::exportOnetab)
            .joinToString("\n\n")

    /**
     * Converts this object into JSON format.
     *
     * eg. {"groups":[]}
     *
     * Used when transmitting multiple link groups via a HTTP request.
     * */
    fun exportJson(): String =
        Json.encodeToString(this)

    companion object{

        /**
         * Converts multiple tab groups from Onetab into a LinkGroupArray.
         *
         * @param str String containing one or more double-newline-delimited Onetab
         * tab groups
         *
         * @return A LinkGroupArray object
         */
        fun importOnetab(str: String) =
            str.split("\n\n")
                .map(LinkGroup::importOnetab)
                .let(::LinkGroupArray)

        /**
         * Converts a JSON string into a LinkGroupArray object.
         *
         * @param str JSON string to convert to a link group array
         *
         * @return A LinkGroupArray object
         * */
        fun importJson(str: String) =
            Json.decodeFromString<LinkGroupArray>(str)

    }

}