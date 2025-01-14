package vn.id.tool.nlu.api.nlu

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes
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
        var constant : Constant? = null
        @OptIn(InternalResourceApi::class, ExperimentalResourceApi::class)
         suspend fun createConstant(): Constant {
            val json = Res.readBytes("files/api.json").toString(Charsets.UTF_8)
            constant = Json.decodeFromString<Constant>(json)
            return constant as Constant
        }
    }
}