package dev.mcarr.la.interfaces

import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.classes.LinkGroupArray

/**
 * Represents a source of data from which this application
 * can return results through API calls.
 *
 * At this point in time, the application always retrieves
 * data from a database, so there is only one data source.
 *
 * However, the application could hypothetically be
 * extended to support other data sources, such as a
 * remote endpoint, or a flat file, in which case a new
 * data source which inherits from this interface
 * would be needed.
 * */
interface IDataSource {

    /**
     * Retrieves a LinkGroup from the data source which
     * matches the given ID, or null if one isn't found.
     *
     * @param id ID of the LinkGroup to retrieve
     *
     * @return LinkGroup with the requested ID, or null
     * if one isn't found
     * */
    fun getLinkGroup(id: Int): LinkGroup?

    /**
     * Deletes a LinkGroup from the data source which
     * matches the given ID.
     *
     * @param id ID of the LinkGroup to delete
     *
     * @return True if the LinkGroup was deleted
     * */
    fun deleteLinkGroup(id: Int): Boolean

    /**
     * Updates a LinkGroup stored within the data source,
     * whereby the new LinkGroup overwrites the old one
     * with the same ID.
     *
     * @param row New LinkGroup which will overwrite the
     * old one
     *
     * @return True if the LinkGroup was updated successfully
     * */
    fun updateLinkGroup(row: LinkGroup): Boolean

    /**
     * Inserts a new LinkGroup into the data source.
     *
     * @param row New LinkGroup to insert into the data source
     *
     * @return Data source row ID of the newly inserted LinkGroup
     * */
    fun insertLinkGroup(row: LinkGroup): Int

    /**
     * Retrieves all LinkGroups in the data source.
     *
     * @return List of all LinkGroups found in the data source
     * */
    fun getLinkGroups(): List<LinkGroup>

    /**
     * Deletes all LinkGroups in the data source.
     *
     * @return Number of LinkGroups which were deleted
     * */
    fun deleteLinkGroups(): Int

}