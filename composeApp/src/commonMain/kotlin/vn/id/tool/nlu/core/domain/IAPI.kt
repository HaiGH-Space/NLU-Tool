package vn.id.tool.nlu.core.domain

import vn.id.tool.nlu.core.network.Error
import vn.id.tool.nlu.core.network.NetworkError
import vn.id.tool.nlu.core.network.Result
import vn.id.tool.nlu.domain.Student


interface IAPI {
    suspend fun login(username: String, password: String): Result<Student, Error>
}