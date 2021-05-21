package com.codingwithmitch.acnhcompanion.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingwithmitch.acnhcompanion.cache.RecipeDao
import com.codingwithmitch.acnhcompanion.cache.model.RecipeEntity

@Database(entities = [RecipeEntity::class ], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object{
        val DATABASE_NAME: String = "recipe_db"
    }


}