package vn.id.tool.nlu.domain

import kotlinx.datetime.LocalDate

data class Semester(
    val idName: String = "",
    val description: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null
)