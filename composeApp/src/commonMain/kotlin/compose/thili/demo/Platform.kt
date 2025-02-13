package compose.thili.demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform