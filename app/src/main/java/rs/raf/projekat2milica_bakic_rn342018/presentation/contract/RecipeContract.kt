package rs.raf.projekat2milica_bakic_rn342018.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2milica_bakic_rn342018.data.model.Recipe
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.AddRecipeState
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.RecipeDetailsState
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.RecipeState
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.SaveRecipeState

class RecipeContract {

    interface ViewModel {

        val recipesState: LiveData<RecipeState>
        val addDone: LiveData<AddRecipeState>
        val recipeDetailsState: LiveData<RecipeDetailsState>
        val saveRecipeState: LiveData<SaveRecipeState>

        fun fetchAllRecipesByQ(q : String)
        fun getAllRecipes()
        fun getRecipesByName(name: String)
        fun addRecipe(recipe: Recipe)

        fun fetchRecipeDetailsByID(id : String)
        fun getRecipeDetailsByID(id : String)

        fun getAllSavedRecipes()
        fun addSavedRecipe(recipeToSave: SavedRecipe)
    }

}