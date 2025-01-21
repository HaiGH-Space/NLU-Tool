package vn.id.tool.nlu.data.nlu.network

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import vn.id.tool.nlu.res.Res
@Serializable
class Constant(
    val root: String,
    val prefix: String,
    val getUrl: String,
    val locThongTin: String,
    val login: String,
    val loginPing: String,
    val getCurrentLogin: String,
    val logOut: String,
    val getUserOnline: String,
    val locDsNhomTo: String,
    val locSinhVienInfo: String,
    val locGiangVienInfo: String,
    val locDsDiemSinhVien: String,
    val locDsDieuKienLoc: String,
    val locDsKQdkmhSinhVien: String,
    val xuLydkmhSinhVien: String
) {
    companion object {
        private var constant: Constant? = null
        private var isInitialized = false

        fun getInstance(): Constant {
            if (constant == null && !isInitialized) {
                constant = createConstant()
                isInitialized = true
            }
            return constant as Constant
        }

        @OptIn(ExperimentalResourceApi::class)
        private fun createConstant(): Constant {
            return runBlocking {
                val json = Res.readBytes("files/api.json").toString(Charsets.UTF_8)
                Json.decodeFromString<Constant>(json)
            }
        }
    }
}