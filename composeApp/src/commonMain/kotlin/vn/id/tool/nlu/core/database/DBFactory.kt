package vn.id.tool.nlu.core.database

expect class DBFactory {
    fun createDatabase(): AppDatabase
}