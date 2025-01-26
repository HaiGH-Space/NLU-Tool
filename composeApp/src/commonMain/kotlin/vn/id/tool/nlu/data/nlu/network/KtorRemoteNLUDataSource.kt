package vn.id.tool.nlu.data.nlu.network

import io.ktor.client.HttpClient
import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import vn.id.tool.nlu.core.domain.MainAPI
import vn.id.tool.nlu.core.network.Error
import vn.id.tool.nlu.core.network.Result
import vn.id.tool.nlu.core.network.map
import vn.id.tool.nlu.data.nlu.dto.ListSemesterDto
import vn.id.tool.nlu.data.nlu.dto.StudentDto
import vn.id.tool.nlu.data.nlu.dto.TimetableDto
import vn.id.tool.nlu.data.nlu.mappers.toSemesters
import vn.id.tool.nlu.data.nlu.mappers.toStudent
import vn.id.tool.nlu.data.nlu.mappers.toTimetable
import vn.id.tool.nlu.domain.Semester
import vn.id.tool.nlu.domain.Student
import vn.id.tool.nlu.domain.Timetable

class KtorRemoteNLUDataSource(client: HttpClient): MainAPI(client) {
    override suspend fun login(username: String, password: String): Result<Student, Error> {
        val urlString = RequestUtil.requestWithAPIUrl(Constant.getInstance().login)
        val method = HttpMethod.Post
        val body = "username=$username&password=$password&grant_type=password"
        return request<StudentDto>(urlString = urlString, method = method, body = body).map {
            it.toStudent()
        }
    }

    override suspend fun timetable(semester: Semester): Result<Timetable, Error> {
        val urlString =
            RequestUtil.requestWithAPIUrlPublic(Constant.getInstance().locDsTkbHocKyTheoDoiTuong)
        val method = HttpMethod.Post
        val headers = getHeaderBuilder()
        val body = "{hoc_ky: ${semester.idName}, loai_doi_tuong: 1, id_du_lieu: null}"
        return request<TimetableDto>(
            urlString = urlString,
            method = method,
            body = body,
            headers = headers
        ).map {
            it.toTimetable()
        }
    }

    override suspend fun getListSemester(): Result<List<Semester>, Error> {
        val urlString =
            RequestUtil.requestWithAPIUrlPublic(Constant.getInstance().locDsHocKyTkbUser)
        val method = HttpMethod.Post
        val headers = getHeaderBuilder()
        val body = "{\"filter\":{\"is_tieng_anh\":null},\"additional\":{\"paging\":{\"limit\":100,\"page\":1},\"ordering\":[{\"name\":\"hoc_ky\",\"order_type\":1}]}}"
        return request<ListSemesterDto>(
            urlString = urlString,
            method = method,
            body = body,
            headers = headers
        ).map {
            it.toSemesters()
        }
    }

    private fun getHeaderBuilder() = Headers.build {
        append("authorization", "Bearer ${Student.student!!.accessToken}")
        append("Content-Type", "application/json")
    }


}