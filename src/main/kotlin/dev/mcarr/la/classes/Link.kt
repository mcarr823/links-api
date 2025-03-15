package dev.mcarr.la.classes

import kotlinx.serialization.Serializable

/**
 * Represents a URL displayed on the website.
 *
 * @param name Name of the website. eg. GitHub
 * @param url URL of the website. eg. https://github.com
 * @param favicon Name of the favicon, if one has been downloaded.
 * Otherwise it should be an empty string.
 */
@Serializable
class Link(
    val name: String,
    val url: String,
    val favicon: String
){

    /**
     * Get the filesystem path of the favicon.
     *
     * @return Path to the favicon, or an empty string if one
     * hasn't been downloaded yet.
     */
    fun faviconPath(): String =
        if (favicon.isEmpty()) "" else "/data/favicons/$favicon"

    /**
     * Exports the link in Onetab's format, which is
     * $url | $name
     * eg. https://github.com | Github
     *
     * @return String in the format of $url | $name
     */
    fun exportOnetab(): String =
        "$url | $name"

    companion object{

        /**
         * Converts a tab from Onetab into a Link.
         *
         * @param str String containing a single Onetab tab
         *
         * @return A Link object
         */
        fun importOnetab(str: String): Link {
            val chunks = str.split("|")
            return Link(
                name = chunks[1].trim(),
                url = chunks[0].trim(),
                favicon = ""
            )
        }

    }
}