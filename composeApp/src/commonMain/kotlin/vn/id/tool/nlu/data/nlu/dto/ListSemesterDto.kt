package vn.id.tool.nlu.data.nlu.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListSemesterDto(
    val data : SemestersDataDto
)
@Serializable
data class SemestersDataDto(
    @SerialName("ds_hoc_ky")
    val listSemesterDto: List<SemesterDto>
)
@Serializable
data class SemesterDto(
    @SerialName("hoc_ky")
    val idName: Int,
    @SerialName("ngay_bat_dau_hk") //"17/02/2025"
    val startSemester:String?,
    @SerialName("ngay_ket_thuc_hk") //"07/07/2025"
    val endSemester:String?,
    @SerialName("ten_hoc_ky")
    val description: String,
)
