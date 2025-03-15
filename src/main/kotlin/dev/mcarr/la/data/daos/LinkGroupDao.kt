package dev.mcarr.la.data.daos

import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.data.tables.LinkGroupTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.transaction.annotation.Transactional

/**
 * Data access object for querying LinkGroup objects in the database.
 *
 * Links are converted from LinkGroupTable entities into LinkGroup objects.
 *
 * @see LinkGroupTable
 * @see LinkGroup
 * */
@Transactional
object LinkGroupDao {

    /**
     * Retrieves all LinkGroup objects from the database.
     *
     * @return Every LinkGroup in the database
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun getAll(): List<LinkGroup> =
        LinkGroupTable.selectAll()
            .map(LinkGroupTable::toLinkGroup)

    /**
     * Retrieves a single LinkGroup from the database.
     *
     * @param id ID of the LinkGroup to retrieve
     *
     * @return The requested LinkGroup, or null if it could
     * not be found
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun get(id: Int): LinkGroup? =
        LinkGroupTable.selectAll()
            .where { LinkGroupTable.id eq id }
            .singleOrNull()
            ?.let(LinkGroupTable::toLinkGroup)

    /**
     * Inserts a LinkGroup object into the database.
     *
     * @param gName Name of the new LinkGroup to create
     *
     * @return Database row ID of the successfully inserted LinkGroup
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun insert(gName: String): Int =
        LinkGroupTable.insert {
            it[name] = gName
        } get LinkGroupTable.id

    /**
     * Updates a LinkGroup object in the database.
     *
     * @param linkGroup Updated LinkGroup object to replace
     * the existing one in the database
     *
     * @return True if the link group was updated successfully
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun update(linkGroup: LinkGroup): Boolean {
        val updatedCount = LinkGroupTable.update({ LinkGroupTable.id eq linkGroup.id }) {
            it[LinkGroupTable.name] = linkGroup.name
        }
        return updatedCount == 1
    }

    /**
     * Deletes a LinkGroup object from the database.
     *
     * @param id ID of the LinkGroup to delete
     *
     * @return True if the link group with the given ID
     * was deleted
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun delete(id: Int): Boolean {
        val deletedCount = LinkGroupTable.deleteWhere {
            LinkGroupTable.id eq id
        }
        return deletedCount == 1
    }

    /**
     * Deletes all LinkGroup objects from the database.
     *
     * @return The number of link groups which were deleted
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun deleteAll(): Int {
        return LinkGroupTable.deleteAll()
    }

}