package vn.id.tool.nlu.api

import vn.id.tool.nlu.api.model.FilteringCondition
import vn.id.tool.nlu.api.model.Student
import vn.id.tool.nlu.network.NetworkError
import vn.id.tool.nlu.network.Result

interface IAPI {
    suspend fun login(username: String, password: String): Result<Student, NetworkError>
    suspend fun dsDKLoc(authorization: String): Result<List<FilteringCondition>, NetworkError>
}