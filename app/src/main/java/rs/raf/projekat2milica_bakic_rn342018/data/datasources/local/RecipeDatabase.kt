package rs.raf.projekat2milica_bakic_rn342018.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat2milica_bakic_rn342018.data.datasources.converter.DateConvertor
import rs.raf.projekat2milica_bakic_rn342018.data.datasources.converter.StringListConverter
import rs.raf.projekat2milica_bakic_rn342018.data.model.MealEntity
import rs.raf.projekat2milica_bakic_rn342018.data.model.RecipeEntity
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipeEntity

@Database(
        entities = [RecipeEntity::class, MealEntity::class, SavedRecipeEntity::class],
        version = 1,
        exportSchema = false)
@TypeConverters(StringListConverter::class, DateConvertor::class)
abstract class RecipeDatabase :  RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao
    abstract fun getMealDetailsDao(): MealDetailsDao
    abstract fun getSavedRecipeDao(): SavedRecipeDao
}