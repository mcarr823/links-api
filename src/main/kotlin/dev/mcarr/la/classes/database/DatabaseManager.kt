package dev.mcarr.la.classes.database

import dev.mcarr.la.data.enums.DatabaseType
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.sql.DataSource

/**
 * Spring class for managing the database connection.
 *
 * This class interprets environment variables and figures
 * out which database the server should connect to.
 * */
@Configuration
@Component
class DatabaseManager {

    /**
     * The type of database to connect to.
     *
     * This value should match a known DatabaseType.
     *
     * @see DatabaseType
     * */
    @Value("\${DB_TYPE:MEMORY}")
    private lateinit var dbType: String

    /**
     * Server on which the database resides.
     * */
    @Value("\${DB_HOST:localhost}")
    private lateinit var dbHost: String

    /**
     * Port on which the database is running.
     *
     * A default value will be used based on dbType
     * if a port is not specified.
     * */
    @Value("\${DB_PORT:}")
    private lateinit var dbPort: String

    /**
     * Name of the database to connect to.
     *
     * May be empty if the DBMS doesn't require/support
     * a database name.
     * */
    @Value("\${DB_NAME:}")
    private lateinit var dbName: String

    /**
     * Username credential used to connect to the database.
     *
     * May be empty if the database doesn't require
     * authentication
     * */
    @Value("\${DB_USER:root}")
    private lateinit var dbUser: String

    /**
     * Password credential used to connect to the database.
     *
     * May be empty if the database doesn't require
     * authentication
     * */
    @Value("\${DB_PASS:}")
    private lateinit var dbPass: String

    /**
     * Location of the database file.
     *
     * Only required for SQLite.
     * */
    @Value("\${DB_FILE:}")
    private lateinit var dbFile: String

    /**
     * Spring bean for managing the database connection.
     * */
    @get:Bean
    val dataSource: DataSource
        get() = connect()

    /**
     * Establishes a connection to a database based on the
     * environment variables.
     *
     * @throws Exception If the provided database type is not in
     * a known or supported format, or if a required connection
     * parameter has not been provided, or if the database
     * connection is unsuccessful for some reason
     *
     * @see DatabaseType
     * */
    private fun connect(): DataSource {
        val type = parseDatabaseType()
        val source = DataSourceBuilder.create()
        when(type){
            DatabaseType.MEMORY -> source.connectMemory()
            DatabaseType.MARIADB -> source.connectMaria()
            DatabaseType.MYSQL -> source.connectMySql()
            DatabaseType.POSTGRES -> source.connectPostgres()
            DatabaseType.SQLITE -> source.connectSqLite()
        }
        return source.build()
    }

    /**
     * Determines what type of database the user is trying to
     * connect to.
     *
     * This program only supports the database formats defined
     * in the DatabaseType enum.
     * So this function will attempt to map the provided database
     * type to a known value in the DatabaseType enum.
     *
     * @return The matched DatabaseType enum
     *
     * @throws Exception If the provided database type is not in
     * a known or supported format
     *
     * @see DatabaseType
     * */
    private fun parseDatabaseType(): DatabaseType {
        //println("Database type: $dbType")
        return when(dbType){
            DatabaseType.MEMORY.type -> DatabaseType.MEMORY
            DatabaseType.MARIADB.type -> DatabaseType.MARIADB
            DatabaseType.MYSQL.type -> DatabaseType.MYSQL
            DatabaseType.POSTGRES.type -> DatabaseType.POSTGRES
            DatabaseType.SQLITE.type -> DatabaseType.SQLITE
            else -> throw Exception("Database type unknown")
        }
    }

    /**
     * Attempts to establish a connection to a RAM database.
     *
     * This should never fail, and should only really be used for
     * testing purposes, since it doesn't persist data.
     * */
    private fun DataSourceBuilder<*>.connectMemory(){
        driverClassName("org.h2.Driver")
        url("jdbc:h2:mem:regular")
    }

    /**
     * Attempts to establish a connection to a MariaDB database.
     *
     * @throws Exception If a required connection parameter has
     * not been provided, or if the database connection is
     * unsuccessful for some reason
     * */
    private fun DataSourceBuilder<*>.connectMaria(){
        val port = dbPort.toIntOrNull() ?: "3306"
        val name = dbName.takeIf { it.isNotEmpty() } ?: throw Exception("Database name not specified")

        driverClassName("org.mariadb.jdbc.Driver")
        url("jdbc:mariadb://$dbHost:$port/$name")
        username(dbUser)
        password(dbPass)
    }

    /**
     * Attempts to establish a connection to a MySQL database.
     *
     * @throws Exception If a required connection parameter has
     * not been provided, or if the database connection is
     * unsuccessful for some reason
     * */
    private fun DataSourceBuilder<*>.connectMySql(){
        val port = dbPort.toIntOrNull() ?: "3306"
        val name = dbName.takeIf { it.isNotEmpty() } ?: throw Exception("Database name not specified")

        driverClassName("com.mysql.cj.jdbc.Driver")
        url("jdbc:mysql://$dbHost:$port/$name")
        username(dbUser)
        password(dbPass)
    }

    /**
     * Attempts to establish a connection to a Postgres database.
     *
     * @throws Exception If a required connection parameter has
     * not been provided, or if the database connection is
     * unsuccessful for some reason
     * */
    private fun DataSourceBuilder<*>.connectPostgres(){
        val port = dbPort.toIntOrNull() ?: "12346"
        val name = dbName.takeIf { it.isNotEmpty() } ?: throw Exception("Database name not specified")

        driverClassName("org.postgresql.Driver")
        url("jdbc:postgresql://$dbHost:$port/$name")
        username(dbUser)
        password(dbPass)
    }

    /**
     * Attempts to establish a connection to a SQLite database.
     *
     * @throws Exception If a required connection parameter has
     * not been provided, or if the database connection is
     * unsuccessful for some reason
     * */
    private fun DataSourceBuilder<*>.connectSqLite(){
        val fp = dbFile.takeIf { it.isNotEmpty() } ?: throw Exception("SQLite DB file not specified")
        //TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        driverClassName("org.sqlite.JDBC")
        url("jdbc:sqlite:$fp")
    }

}