package com.z3r0_8ug.acnhcompanion.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.z3r0_8ug.acnhcompanion.cache.RecipeDao
import com.z3r0_8ug.acnhcompanion.cache.model.RecipeEntity

@Database(entities = [RecipeEntity::class ], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object{
        val DATABASE_NAME: String = "recipe_db"
    }


}