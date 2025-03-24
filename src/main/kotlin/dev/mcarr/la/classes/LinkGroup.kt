package dev.mcarr.la.classes

import kotlinx.serialization.Serializable

/**
 * Represents a group of links.
 *
 * TODO add other parameters, such as color, icon, etc.
 *
 * @param id Auto generated ID which uniquely identifies
 * this LinkGroup
 * @param name Name of the link group
 * @param links The links which belong to this group
 *
 * @see Link
*/
@Serializable
class LinkGroup(
    val id: Int,
    val name: String,
    val links: List<Link>
){

    /**
     * Exports each link in Onetab's format, then puts them all
     * in a newline-delimited string.
     *
     * @return Newline-delimited string of links in the Onetab format
     */
    fun exportOnetab(): String =
        links.joinToString("\n", transform = Link::exportOnetab)

    /**
     * Creates a clone of the LinkGroup object with the provided
     * Link objects inserted into it.
     *
     * This is required since the `links` value is immutable.
     *
     * @param links List of links to add to the cloned LinkGroup
     *
     * @return Clone of this LinkGroup object, but with the provided
     * list of links added to it
     * */
    fun clone(links: List<Link>) =
        LinkGroup(
            id = id,
            name = name,
            links = links
        )

    companion object{

        const val ID = "id"
        const val NAME = "name"
        const val LINKS = "links"

        /**
         * Converts a tab group from Onetab into a LinkGroup.
         *
         * @param str String containing one or more newline-delimited Onetab tabs
         *
         * @return A LinkGroup object
         */
        fun importOnetab(str: String): LinkGroup {
            val links = str.split("\n")
                .filter(String::isNotEmpty)
                .map(Link::importOnetab)
            return LinkGroup(
                id = 0,
                name = "",
                links = links
            )
        }

    }

}