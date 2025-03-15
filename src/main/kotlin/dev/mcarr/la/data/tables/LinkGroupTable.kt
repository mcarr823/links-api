package dev.mcarr.la.data.tables

import dev.mcarr.la.classes.LinkGroup
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

/**
 * Database table of LinkGroup objects.
 *
 * @see LinkGroup
 * */
object LinkGroupTable : Table() {

    /**
     * Auto generated ID which uniquely identifies this LinkGroup
     * */
    val id = integer("lg_id").autoIncrement()

    /**
     * Name of the link group
     * */
    val name = varchar(name = "lg_name", length = 255)

    override val primaryKey = PrimaryKey(id)

    /**
     * Converts a database row (a LinkGroupTable entity) into
     * a LinkGroup object.
     *
     * @param row The row of data retrieved from the database
     *
     * @return LinkGroup generated from the database data
     * */
    fun toLinkGroup(row: ResultRow): LinkGroup =
        LinkGroup(
            id = row[id],
            name = row[name],
            links = listOf()
        )
}