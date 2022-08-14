package rs.raf.projekat2milica_bakic_rn342018.presentation.states

import rs.raf.projekat2milica_bakic_rn342018.data.model.Meal

sealed class RecipeDetailsState {

    object DataFetched: RecipeDetailsState()
    data class Success(val meal: Meal): RecipeDetailsState()
    data class Error(val message: String): RecipeDetailsState()
}