package vn.id.tool.nlu.core.domain

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import vn.id.tool.nlu.core.network.Error
import vn.id.tool.nlu.core.network.NetworkError
import vn.id.tool.nlu.core.network.NetworkErrorExt
import vn.id.tool.nlu.core.network.Result

abstract class MainAPI(
    protected val client: HttpClient
): IAPI {
    protected suspend inline fun <reified T> request(
        urlString: String,
        method: HttpMethod,
        parameters: Parameters? = null,
        headers: Headers? = null,
        body: Any? = null
    ): Result<T, Error> {
        val response = try {
            client.request(
                urlString = urlString,
            ) {
                this.method = method
                headers {
                    headers?.let { appendAll(it) }
                }
                io.ktor.http.parameters {
                    parameters?.let {
                        io.ktor.http.parameters { appendAll(it) }
                    }
                }
                body?.let {
                    setBody(it)
                }
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        return response(response)
    }

    protected suspend inline fun <reified T> response(response: HttpResponse): Result<T, Error> {
        return when (response.status.value) {
            in 200..299 -> {
//                println(response.bodyAsText())
                val data = response.body<T>()
                Result.Success(data)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkErrorExt(response.bodyAsText()))
        }
    }
}