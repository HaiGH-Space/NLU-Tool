package vn.id.tool.nlu.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val principal: String?,
    val name: String?,
    val refresh_token: String?,
    val expires_in: Int?,
    val token_type: String?,
    val access_token: String?
) {
    val authorization: String = "$token_type $access_token"
    override fun toString(): String {
        return "Student{" +
                "mail='" + principal + '\'' +
                ", name='" + name + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", token_type='" + token_type + '\'' +
                ", access_token='" + access_token + '\'' +
                '}'
    }
}