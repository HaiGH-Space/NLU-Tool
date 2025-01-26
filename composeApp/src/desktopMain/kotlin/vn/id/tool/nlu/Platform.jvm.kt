package vn.id.tool.nlu


class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}
actual class DevicePlatform : Device() {
     override fun isMobile(): Boolean = false
}
actual fun getPlatform(): Platform = JVMPlatform()