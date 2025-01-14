package vn.id.tool.nlu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform