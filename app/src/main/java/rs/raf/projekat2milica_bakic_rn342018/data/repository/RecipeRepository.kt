package rs.raf.projekat2milica_bakic_rn342018.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2milica_bakic_rn342018.data.model.Meal
import rs.raf.projekat2milica_bakic_rn342018.data.model.Recipe
import rs.raf.projekat2milica_bakic_rn342018.data.model.Resources
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe

interface RecipeRepository {

    fun fetchAll(q : String): Observable<Resources<Unit>>
    fun getAll(): Observable<List<Recipe>>
    fun getAllByName(name: String): Observable<List<Recipe>>
    fun insert(recipe: Recipe): Completable

    //MealDetailsDao
    fun fetchRecipeDetails(id : String): Observable<Resources<Unit>>
    fun getRecipeByID(id : String): Observable<Meal>

    //SavedRecipeDao
    fun getSavedRecipies(): Observable<List<SavedRecipe>>
    fun insertSavedRecipe(recipeToSave: SavedRecipe): Completable

}