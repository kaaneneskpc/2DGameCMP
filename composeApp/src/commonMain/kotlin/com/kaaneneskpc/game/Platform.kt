package com.kaaneneskpc.game

enum class Platform {
    Android,
    iOS,
    Desktop,
    Web
}

expect fun getPlatform(): Platform