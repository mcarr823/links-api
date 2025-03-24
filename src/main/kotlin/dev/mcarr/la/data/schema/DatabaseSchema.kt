package dev.mcarr.la.data.schema

import dev.mcarr.la.data.tables.LinkGroupTable
import dev.mcarr.la.data.tables.LinkTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * This class is responsible for initializing the database.
 *
 * It should be updated whenever a new database table is created.
 * */
@Component
@Transactional
class DatabaseSchema : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {

        // Creates the LinkGroup table
        SchemaUtils.create(LinkGroupTable)

        // Creates the Link table
        SchemaUtils.create(LinkTable)

    }
}