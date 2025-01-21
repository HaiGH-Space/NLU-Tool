package vn.id.tool.nlu.data.nlu.network

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import vn.id.tool.nlu.core.domain.MainAPI
import vn.id.tool.nlu.core.network.Error
import vn.id.tool.nlu.core.network.NetworkError
import vn.id.tool.nlu.core.network.Result
import vn.id.tool.nlu.core.network.map
import vn.id.tool.nlu.data.nlu.dto.StudentDto
import vn.id.tool.nlu.data.nlu.mappers.toStudent
import vn.id.tool.nlu.domain.Student

class KtorRemoteNLUDataSource(client: HttpClient): MainAPI(client) {
    override suspend fun login(username: String, password: String): Result<Student, Error> {
        val urlString = RequestUtil.requestWithAPIUrl(Constant.getInstance().login)
        val method = HttpMethod.Post
        val body = "username=$username&password=$password&grant_type=password"
        return request<StudentDto>(urlString = urlString, method = method, body = body).map {
            it.toStudent()
        }
    }

}