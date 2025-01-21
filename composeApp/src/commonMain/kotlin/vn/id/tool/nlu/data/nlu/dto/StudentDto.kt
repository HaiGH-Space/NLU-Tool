package vn.id.tool.nlu.data.nlu.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentDto(
    @SerialName("access_token")
    val accessToken: String,
    val code: String,
    val id: String,
    val name: String,
    @SerialName("principal")
    val email: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    val result: String,
    val roles: String,
    @SerialName("token_type")
    val tokenType: String,
    val userName: String,
)