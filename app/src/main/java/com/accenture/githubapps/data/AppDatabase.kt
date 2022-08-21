package com.accenture.githubapps.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.accenture.githubapps.data.model.FavoriteDao
import com.accenture.githubapps.data.model.TempFavoriteUserDelete
import com.accenture.githubapps.data.model.FavoriteUser
import com.accenture.githubapps.data.model.Follower
import com.accenture.githubapps.utils.DATABASE_NAME

@Database(
    entities = [
        FavoriteUser::class,
        Follower::class,
        TempFavoriteUserDelete::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    /* --------------------------------- Provide DAO Survey EMR ----------------------*/
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        private val  MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Update this when do migration
        // ref : https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        // https://infinum.com/handbook/books/android/common-android/room-migrations
        // https://code.luasoftware.com/tutorials/android/android-room-upgrade-database-new-table/
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                //.addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}