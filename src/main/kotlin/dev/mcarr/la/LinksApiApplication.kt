package dev.mcarr.la

import dev.mcarr.la.classes.database.DatabaseManager
import dev.mcarr.la.data.enums.DatabaseEnvironment
import dev.mcarr.la.data.enums.DatabaseType
import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * The main application class.
 *
 * Most of this app is abstracted by Spring tags, so the
 * main function doesn't do a lot.
 *
 * The tags are the important part of this class, which tell
 * Spring how this application should work.
 * */
@SpringBootApplication
@ImportAutoConfiguration(ExposedAutoConfiguration::class)
class LinksApiApplication

/**
 * This application's main entry point.
 *
 * This application doesn't receive any arguments.
 * Instead, it relies on environment variables to
 * tell it how to run.
 *
 * @param args CLI arguments passed to the application
 * when it is started. These are currently ignored
 * */
fun main(args: Array<String>) {

	val dbType = DatabaseManager.getEnv(DatabaseEnvironment.TYPE, DatabaseType.MEMORY.type)
	val dbHost = DatabaseManager.getEnv(DatabaseEnvironment.HOST, "localhost")
	val dbPort = DatabaseManager.getEnv(DatabaseEnvironment.PORT)
	val dbName = DatabaseManager.getEnv(DatabaseEnvironment.NAME)
	val dbUser = DatabaseManager.getEnv(DatabaseEnvironment.USER, "root")
	val dbPass = DatabaseManager.getEnv(DatabaseEnvironment.PASS, "")
	val dbFile = DatabaseManager.getEnv(DatabaseEnvironment.FILE)

	DatabaseManager.connect(
		dbType = dbType,
		dbHost = dbHost,
		dbPort = dbPort,
		dbName = dbName,
		dbUser = dbUser,
		dbPass = dbPass,
		dbFile = dbFile
	)

	runApplication<LinksApiApplication>(*args)
}
