package com.alievisa.bergersteak.domain

interface Error

sealed interface DataError: Error {
    enum class Remote: DataError {
        BAD_REQUEST,
        UNAUTHORIZED,
        REQUEST_TIMEOUT,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        INTERNAL_SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}