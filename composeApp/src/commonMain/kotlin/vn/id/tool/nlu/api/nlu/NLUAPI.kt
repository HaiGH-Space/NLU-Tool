package vn.id.tool.nlu.api.nlu

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import vn.id.tool.nlu.api.IAPI
import vn.id.tool.nlu.api.model.Student
import vn.id.tool.nlu.network.NetworkError
import vn.id.tool.nlu.network.Result
class NLUAPI(private val client: HttpClient) : IAPI {
    override suspend fun login(username: String, password: String): Result<Student, NetworkError> {
        val reponse = try {
            client.post(
                urlString = RequestUtil.requestWithAPIUrl(Constant.constant!!.login)
            ) {
                setBody("username=21130343&password=hoctapdh01&grant_type=password")
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        println(reponse.bodyAsText())
        return when (reponse.status.value) {
            in 200..299 -> {
                val data = reponse.body<Student>()
                Result.Success(data)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}