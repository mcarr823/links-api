package dev.mcarr.la.data.daos

import dev.mcarr.la.classes.Link
import dev.mcarr.la.data.tables.LinkTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.transaction.annotation.Transactional

/**
 * Data access object for querying Link objects in the database.
 *
 * Links are converted from LinkTable entities into Link objects.
 *
 * @see LinkTable
 * @see Link
 * */
@Transactional
object LinkDao {

    /**
     * Retrieves all Link objects from the database.
     *
     * @return Every Link in the database
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun getAll(): List<Link> {
        return LinkTable.selectAll()
            .map(LinkTable::toLink)
    }

    /**
     * Retrieves a single Link from the database.
     *
     * @param linkId ID of the Link to retrieve
     *
     * @return The requested Link, or null if it could
     * not be found
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun get(linkId: Int): Link? {
        return LinkTable.selectAll()
            .where{ LinkTable.id eq linkId }
            .singleOrNull()
            ?.let(LinkTable::toLink)
    }

    /**
     * Retrieves all Link objects associated with a specific
     * LinkGroup.
     *
     * @param groupId ID of the LinkGroup you want to retrieve
     * the links from
     *
     * @return All links associated with the given group ID
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun getByGroupId(groupId: Int): List<Link> {
        return LinkTable.selectAll()
            .where{ LinkTable.groupId eq groupId }
            .map(LinkTable::toLink)
    }

    /**
     * Inserts a Link object into the database.
     *
     * @param gId ID of the LinkGroup which the Link
     * belongs to
     * @param row The Link to insert into the database
     *
     * @return Database row ID of the successfully inserted Link
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun insert(gId: Int, row: Link): Int {
        return LinkTable.insert {
            it[groupId] = gId
            it[name] = row.name
            it[url] = row.url
            it[favicon] = row.favicon
        } get LinkTable.id
    }

    /**
     * Updates a Link object in the database.
     *
     * @param id ID of the Link to modify
     * @param row The updated Link object to overwrite
     * the old one
     *
     * @return True if the link with the given ID was
     * updated
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun update(id: Int, row: Link): Boolean {
        val updatedCount = LinkTable.update({ LinkTable.id eq id }) {
            it[name] = row.name
            it[url] = row.url
            it[favicon] = row.favicon
        }
        return updatedCount == 1
    }

    /**
     * Deletes a Link object from the database.
     *
     * @param id ID of the Link to delete
     *
     * @return True if the link with the given ID was
     * deleted
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun delete(id: Int): Boolean {
        val deletedCount = LinkTable.deleteWhere {
            LinkTable.id eq id
        }
        return deletedCount == 1
    }

    /**
     * Deletes all Link objects from the database.
     *
     * @return The number of links which were deleted
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun deleteAll(): Int {
        return LinkTable.deleteAll()
    }

    /**
     * Deletes all Link objects which belong to a specific LinkGroup.
     *
     * @param groupId ID of the LinkGroup which should have its
     * associated Link objects deleted from the database
     *
     * @return The number of links which were deleted
     *
     * @throws Exception If the database query failed
     * */
    @Throws(Exception::class)
    fun deleteByGroupId(groupId: Int): Int {
        return LinkTable.deleteWhere {
            LinkTable.groupId eq groupId
        }
    }

}