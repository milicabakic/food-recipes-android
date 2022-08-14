package rs.raf.projekat2milica_bakic_rn342018.presentation.states

import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe

sealed class SaveRecipeState {

    object DataFetched: SaveRecipeState()
    data class Success(val recipes: List<SavedRecipe>): SaveRecipeState()
    object SuccessfullySaved: SaveRecipeState()
    data class Error(val message: String): SaveRecipeState()
}