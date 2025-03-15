package dev.mcarr.la.data.enums

/**
 * Enum containing all known database formats which this
 * program supports.
 *
 * @param type String representation of the given
 * database format. Required for converting environment
 * arguments to/from the enum type.
 * */
enum class DatabaseType(
    val type: String
) {
    MEMORY("MEMORY"),
    MARIADB("MARIADB"),
    MYSQL("MYSQL"),
    POSTGRES("POSTGRES"),
    SQLITE("SQLITE")
}