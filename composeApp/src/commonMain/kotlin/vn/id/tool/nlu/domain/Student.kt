package vn.id.tool.nlu.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector

data class Student(
    val id:String,
    val username:String = "",
    val name:String,
    val email:String = "",
    var accessToken:String? = "",
    val avatar : ImageVector = Icons.Outlined.AccountCircle
){
    companion object {
        var student: Student? = null
    }
}
