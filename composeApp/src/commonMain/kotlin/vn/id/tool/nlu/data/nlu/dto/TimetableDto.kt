package vn.id.tool.nlu.data.nlu.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimetableDto(
    @SerialName("data")
    val data: DataTimetableDto? = null
)
@Serializable
data class DataTimetableDto(
    @SerialName("ds_nhom_to")
    val classSchedule: List<ClassScheduleDto>
)
@Serializable
data class ClassScheduleDto(
    @SerialName("ma_mon")
    val id: String,
    @SerialName("ten_mon")
    val name: String,
    @SerialName("khoi")
    val className: String,//DH21XYZ
    val tkb: String,//05/09/23 đến 14/11/23
    @SerialName("tu_gio")
    val startTime : String = "00:00",
    @SerialName("den_gio")
    val endTime: String = "00:00",
    @SerialName("gv")
    val teacher: String = "",
    @SerialName("phong")
    val room: String = "",
    @SerialName("so_tc")
    val credit: String = "0",
    @SerialName("thu")
    val dayOfWeek: Int,
    @SerialName("tbd")
    val period : Int,
    @SerialName("so_tiet_lt")
    val numOfTheo: Int = 0,
    @SerialName("so_tiet_th")
    val numOfLab: Int = 0,
    @SerialName("tooltip")
    val weeks: String
)