package vn.id.tool.nlu.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vn.id.tool.nlu.core.database.model.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    suspend fun getAll(): List<Account>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account)
    @Delete
    suspend fun delete(account: Account)
    @Query("Select * FROM account WHERE username = :username")
    suspend fun getAccountByUsername(username: String): Account
}