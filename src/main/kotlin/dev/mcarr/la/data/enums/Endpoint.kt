package dev.mcarr.la.data.enums

/**
 * Enum containing all known endpoints which this
 * program supports.
 *
 * @param value String representation of the given
 * endpoint, without the leading slash.
 * */
enum class Endpoint(
    val value: String
) {
    LINK("link"),
    LINK_ALL("link/all"),
    GROUP("group"),
    GROUP_ALL("group/all"),
    IMPORT("import"),
    EXPORT("export"),
    HEALTH("health")
}