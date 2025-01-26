package vn.id.tool.nlu.core.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import java.io.File

actual class DBFactory {
   actual fun createDatabase(): AppDatabase {
       val dbFile = File(System.getProperty("java.io.tmpdir"), DATABASE_FILE_NAME)
       return Room.databaseBuilder<AppDatabase>(dbFile.absolutePath)
           .setDriver(BundledSQLiteDriver())
           .setQueryCoroutineContext(Dispatchers.IO)
           .fallbackToDestructiveMigration(true)
           .build()
   }
}