package com.cs4520.assignment5

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DatabaseProvider {

    @SuppressLint("StaticFieldLeak")
    private var database: AppDatabase? = null

    fun getDatabase(context: Context?): AppDatabase {
        if (database == null) {
            if (context != null) {
                database = AppDatabase.makeDatabase(context.applicationContext)
            }
        }
        return database!!
    }
}


@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: AppDatabase? = null

        fun makeDatabase(context: Context): AppDatabase {
            context.let{  return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    it,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            } }
        }
    }

     suspend fun saveProducts(products: List<Product>) {
         withContext(Dispatchers.IO) {
             productDao().insertProducts(products)
         }
    }

  fun retrieveProducts(): List<Product> {
        val productsLiveData = productDao().getAllProducts()
        val products = productsLiveData.value
        return products ?: emptyList()
    }
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Delete
    suspend fun deleteProduct(product: Product)
}

