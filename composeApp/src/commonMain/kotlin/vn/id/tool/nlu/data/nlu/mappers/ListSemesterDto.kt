package vn.id.tool.nlu.data.nlu.mappers

import kotlinx.datetime.LocalDate
import vn.id.tool.nlu.data.nlu.dto.ListSemesterDto
import vn.id.tool.nlu.data.nlu.dto.SemesterDto
import vn.id.tool.nlu.domain.Semester

fun ListSemesterDto.toSemesters() =
    if (this.data.listSemesterDto.size > 4) this.data.listSemesterDto.dropLast(4).map {
        it.toSemester()
    } else this.data.listSemesterDto.map {
        it.toSemester()
    }

fun SemesterDto.toSemester() = Semester(
    idName = this.idName.toString(),
    startDate = this.startSemester?.toLocalDate(),
    endDate = this.endSemester?.toLocalDate(),
    description = this.description
)

fun String.toLocalDate(): LocalDate {
    val (day, month, year) = this.split("/")
    return LocalDate(year.toInt(), month.toInt(), day.toInt())
}