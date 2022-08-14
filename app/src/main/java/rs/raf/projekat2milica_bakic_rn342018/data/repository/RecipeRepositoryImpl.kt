package rs.raf.projekat2milica_bakic_rn342018.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2milica_bakic_rn342018.data.datasources.local.MealDetailsDao
import rs.raf.projekat2milica_bakic_rn342018.data.datasources.local.RecipeDao
import rs.raf.projekat2milica_bakic_rn342018.data.datasources.local.SavedRecipeDao
import rs.raf.projekat2milica_bakic_rn342018.data.datasources.remote.RecipeService
import rs.raf.projekat2milica_bakic_rn342018.data.model.*
import timber.log.Timber

class RecipeRepositoryImpl (
    private val localDataSource: RecipeDao,
    private val localDataSourceMeal: MealDetailsDao,
    private val localDataSourceSavedRecipe: SavedRecipeDao,
    private val remoteDataSource: RecipeService
) : RecipeRepository {

    override fun fetchAll(q : String): Observable<Resources<Unit>> {
        return remoteDataSource
            .getAllByQ(q)
            .doOnNext() {
                Timber.e("Upis u bazu")
                val entities = it.recipes.map {
                    RecipeEntity(
                        it.recipe_id,
                        it.title,
                        it.publisher,
                        it.image_url
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resources.Success(Unit)
            }

    }

    override fun getAll(): Observable<List<Recipe>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Recipe(it.recipe_id, it.title, it.publisher, it.image_url)
                }
            }
    }

    override fun getAllByName(name: String): Observable<List<Recipe>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    Recipe(it.recipe_id, it.title, it.publisher, it.image_url)
                }
            }
    }

    override fun insert(recipe: Recipe): Completable {
        val recipeEntity = RecipeEntity(recipe.recipe_id, recipe.title, recipe.publisher, recipe.image_url)
        return localDataSource
            .insert(recipeEntity)
    }

    override fun fetchRecipeDetails(id: String): Observable<Resources<Unit>> {
        return remoteDataSource
                .getRecipeDetailsByID(id)
                .doOnNext() {
                    val entity = it.recipe
                    val entityToAdd = MealEntity(
                            entity.recipe_id,
                            entity.ingredients,
                            entity.title,
                            entity.image_url
                    )
                    localDataSourceMeal.deleteAndInsertAll(entityToAdd)

                }
                .map {
                    Resources.Success(Unit)
                }
    }

    override fun getRecipeByID(id: String): Observable<Meal> {
        return localDataSourceMeal
                .getByID(id)
                .map {
                    Meal(it.recipe_id, it.ingredients, it.title, it.image_url)
                }
    }

    override fun getSavedRecipies(): Observable<List<SavedRecipe>> {
        return localDataSourceSavedRecipe
                .getAll()
                .map {
                    it.map {
                        SavedRecipe(it.recipe_id, it.ingredients, it.date, it.title, it.image_url, it.category)
                    }
                }
    }

    override fun insertSavedRecipe(recipeToSave: SavedRecipe): Completable {
        val savedRecipeEntity = SavedRecipeEntity(recipeToSave.recipe_id, recipeToSave.ingredients, recipeToSave.date, recipeToSave.title, recipeToSave.image_url, recipeToSave.category)
        return localDataSourceSavedRecipe
                .insert(savedRecipeEntity)
    }

}