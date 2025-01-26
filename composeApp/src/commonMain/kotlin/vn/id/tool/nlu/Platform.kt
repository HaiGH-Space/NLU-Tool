package vn.id.tool.nlu


interface Platform {
    val name: String
}
abstract class Device(){
    abstract fun isMobile(): Boolean
}
expect class DevicePlatform : Device
expect fun getPlatform(): Platform