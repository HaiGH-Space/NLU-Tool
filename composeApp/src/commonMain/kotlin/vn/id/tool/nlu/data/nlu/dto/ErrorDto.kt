package vn.id.tool.nlu.data.nlu.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val code: Int,
    val message: String
)