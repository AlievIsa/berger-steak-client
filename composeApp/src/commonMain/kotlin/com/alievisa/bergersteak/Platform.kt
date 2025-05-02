package com.alievisa.bergersteak

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform