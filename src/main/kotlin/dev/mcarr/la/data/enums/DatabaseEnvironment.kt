package dev.mcarr.la.data.enums

enum class DatabaseEnvironment(
    val key: String
) {
    TYPE("DB_TYPE"),
    HOST("DB_HOST"),
    PORT("DB_PORT"),
    NAME("DB_NAME"),
    USER("DB_USER"),
    PASS("DB_PASS"),
    FILE("DB_FILE")
}