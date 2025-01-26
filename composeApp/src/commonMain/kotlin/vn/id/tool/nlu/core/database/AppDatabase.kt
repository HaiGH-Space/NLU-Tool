package vn.id.tool.nlu.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import vn.id.tool.nlu.core.database.dao.AccountDao
import vn.id.tool.nlu.core.database.model.Account

@Database(
    entities = [Account::class],
    version = 2
)
abstract class AppDatabase :RoomDatabase() {
    abstract fun accountDao(): AccountDao
}
internal const val DATABASE_FILE_NAME = "app_room.db"