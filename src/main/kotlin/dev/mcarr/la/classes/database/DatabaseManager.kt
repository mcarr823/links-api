package dev.mcarr.la.classes.database

import dev.mcarr.la.data.enums.DatabaseEnvironment
import dev.mcarr.la.data.enums.DatabaseType
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

/**
 * Object for managing the connection to known database formats.
 *
 * This class translates the arguments passed to it via the
 * server's environment variables and figures out which
 * database it should connect to as a result.
 * */
object DatabaseManager {

    fun getEnv(
        env: DatabaseEnvironment
    ): String? {
        return System.getenv(env.key)
    }

    fun getEnv(
        env: DatabaseEnvironment,
        defaultValue: String
    ): String {
        return getEnv(env) ?: defaultValue
    }

    /**
     * Establishes a connection to a database based on the
     * arguments passed to this function.
     *
     * @param dbType The type of database to connect to. This value
     * should match a known DatabaseType.
     * @param dbHost Server on which the database resides
     * @param dbPort Port on which the database is running. A default
     * value will be used based on dbType if a port is not specified
     * @param dbName Name of the database to connect to. Can be null
     * if the DBMS doesn't require/support a database name
     * @param dbUser Username credential used to connect to the
     * database. May be empty if the database doesn't require
     * authentication
     * @param dbPass Password credential used to connect to the
     * database. May be empty if the database doesn't require
     * authentication
     * @param dbFile Location of the database file. Only required
     * for SQLite
     *
     * @throws Exception If the provided database type is not in
     * a known or supported format, or if a required connection
     * parameter has not been provided, or if the database
     * connection is unsuccessful for some reason
     *
     * @see DatabaseType
     * */
    fun connect(
        dbType: String,
        dbHost: String,
        dbPort: String?,
        dbName: String?,
        dbUser: String,
        dbPass: String,
        dbFile: String?
    ) {
        val type = parseDatabaseType(dbType)
        when(type){
            DatabaseType.MEMORY -> Database.connect("jdbc:h2:mem:regular", driver = "org.h2.Driver")
            DatabaseType.MARIADB -> connectMaria(dbHost, dbPort, dbName, dbUser, dbPass)
            DatabaseType.MYSQL -> connectMySql(dbHost, dbPort, dbName, dbUser, dbPass)
            DatabaseType.POSTGRES -> connectPostgres(dbHost, dbPort, dbName, dbUser, dbPass)
            DatabaseType.SQLITE -> connectSqLite(dbFile)
        }
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
     * @param type String representation of a DatabaseType value
     *
     * @return The corresponding value from the DatabaseType enum
     *
     * @throws Exception If the provided database type is not in
     * a known or supported format
     *
     * @see DatabaseType
     * */
    private fun parseDatabaseType(
        type: String
    ): DatabaseType {
        return when(type){
            DatabaseType.MEMORY.type -> DatabaseType.MEMORY
            DatabaseType.MARIADB.type -> DatabaseType.MARIADB
            DatabaseType.MYSQL.type -> DatabaseType.MYSQL
            DatabaseType.POSTGRES.type -> DatabaseType.POSTGRES
            DatabaseType.SQLITE.type -> DatabaseType.SQLITE
            else -> throw Exception("Database type unknown")
        }
    }

    /**
     * Attempts to establish a connection to a MariaDB database.
     *
     * @param dbHost Server on which the database resides
     * @param dbPort Port on which the database is running. Defaults
     * to 3306 if not specified
     * @param dbName Name of the database to connect to. An
     * exception will be thrown if this is not provided
     * @param dbUser Username credential used to connect to the
     * database
     * @param dbPass Password credential used to connect to the
     * database
     *
     * @throws Exception If a required connection parameter has
     * not been provided, or if the database connection is
     * unsuccessful for some reason
     * */
    private fun connectMaria(
        dbHost: String,
        dbPort: String?,
        dbName: String?,
        dbUser: String,
        dbPass: String
    ){
        val port = dbPort ?: "3306"
        val name = dbName ?: throw Exception("Database name not specified")
        Database.connect(
            url = "jdbc:mariadb://$dbHost:$port/$name",
            driver = "org.mariadb.jdbc.Driver",
            user = dbUser,
            password = dbPass
        )
    }

    /**
     * Attempts to establish a connection to a MySQL database.
     *
     * @param dbHost Server on which the database resides
     * @param dbPort Port on which the database is running. Defaults
     * to 3306 if not specified
     * @param dbName Name of the database to connect to. An
     * exception will be thrown if this is not provided
     * @param dbUser Username credential used to connect to the
     * database
     * @param dbPass Password credential used to connect to the
     * database
     *
     * @throws Exception If a required connection parameter has
     * not been provided, or if the database connection is
     * unsuccessful for some reason
     * */
    private fun connectMySql(
        dbHost: String,
        dbPort: String?,
        dbName: String?,
        dbUser: String,
        dbPass: String
    ){
        val port = dbPort ?: "3306"
        val name = dbName ?: throw Exception("Database name not specified")
        Database.connect(
            "jdbc:mysql://$dbHost:$port/$name",
            driver = "com.mysql.cj.jdbc.Driver",
            user = dbUser,
            password = dbPass
        )
    }

    /**
     * Attempts to establish a connection to a Postgres database.
     *
     * @param dbHost Server on which the database resides
     * @param dbPort Port on which the database is running. Defaults
     * to 12346 if not specified
     * @param dbName Name of the database to connect to. An
     * exception will be thrown if this is not provided
     * @param dbUser Username credential used to connect to the
     * database
     * @param dbPass Password credential used to connect to the
     * database
     *
     * @throws Exception If a required connection parameter has
     * not been provided, or if the database connection is
     * unsuccessful for some reason
     * */
    private fun connectPostgres(
        dbHost: String,
        dbPort: String?,
        dbName: String?,
        dbUser: String,
        dbPass: String
    ){
        val port = dbPort ?: "12346"
        val name = dbName ?: throw Exception("Database name not specified")
        Database.connect(
            "jdbc:postgresql://$dbHost:$port/$name",
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPass
        )
    }

    /**
     * Attempts to establish a connection to a SQLite database.
     *
     * @param dbFile Location of the database file. An
     * exception will be thrown if this is not provided
     *
     * @throws Exception If a required connection parameter has
     * not been provided, or if the database connection is
     * unsuccessful for some reason
     * */
    private fun connectSqLite(
        filepath: String?
    ){
        val fp = filepath ?: throw Exception("SQLite DB file not specified")
        Database.connect("jdbc:sqlite:$fp", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }

}