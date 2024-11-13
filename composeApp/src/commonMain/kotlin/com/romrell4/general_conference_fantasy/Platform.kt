package com.romrell4.general_conference_fantasy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform