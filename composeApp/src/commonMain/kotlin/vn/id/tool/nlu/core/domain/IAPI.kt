package vn.id.tool.nlu.core.domain

import vn.id.tool.nlu.core.network.Error
import vn.id.tool.nlu.core.network.NetworkError
import vn.id.tool.nlu.core.network.Result
import vn.id.tool.nlu.domain.Semester
import vn.id.tool.nlu.domain.Student
import vn.id.tool.nlu.domain.Timetable


interface IAPI {
    suspend fun login(username: String, password: String): Result<Student, Error>
    suspend fun timetable(semester: Semester): Result<Timetable, Error>
    suspend fun getListSemester(): Result<List<Semester>, Error>
}