package dev.mcarr.la.data.tables

import dev.mcarr.la.classes.Link
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

/**
 * Database table of Link objects.
 *
 * @see Link
 * */
object LinkTable : Table() {

    /**
     * Auto generated ID which uniquely identifies this Link.
     * */
    val id = integer("l_id").autoIncrement()

    /**
     * ID of the LinkGroup to which this Link belongs.
     *
     * Must correspond to a row in the LinkGroup table.
     *
     * @see LinkGroupTable
     * */
    val groupId = integer(name = "l_group_id").references(LinkGroupTable.id, onDelete = ReferenceOption.CASCADE)

    /**
     * Name of the website which this Link is for.
     * */
    val name = varchar(name = "l_name", length = 255)

    /**
     * URL of the website.
     * */
    val url = varchar(name = "l_url", length = 255)

    /**
     * Name of the website's favicon, if one has been downloaded.
     * */
    val favicon = varchar(name = "l_favicon", length = 255)

    override val primaryKey = PrimaryKey(id)

    /**
     * Converts a database row (a LinkTable entity) into
     * a Link object.
     *
     * @param row The row of data retrieved from the database
     *
     * @return Link generated from the database data
     * */
    fun toLink(row: ResultRow): Link =
        Link(
            name = row[name],
            url = row[url],
            favicon = row[favicon]
        )
}