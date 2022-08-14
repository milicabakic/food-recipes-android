package rs.raf.projekat2milica_bakic_rn342018.presentation.states

sealed class AddRecipeState {
    object Success: AddRecipeState()
    data class Error(val message: String): AddRecipeState()
}