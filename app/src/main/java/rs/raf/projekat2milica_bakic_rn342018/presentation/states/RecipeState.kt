package rs.raf.projekat2milica_bakic_rn342018.presentation.states

import rs.raf.projekat2milica_bakic_rn342018.data.model.Recipe

sealed class RecipeState {

    object Loading: RecipeState()
    object DataFetched: RecipeState()
    data class Success(val recipes: List<Recipe>): RecipeState()
    data class Error(val message: String): RecipeState()
}