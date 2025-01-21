package vn.id.tool.nlu.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey val username:String,
    val password:String
)