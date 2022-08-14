package rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe

class SavedRecipesViewModel : ViewModel()  {

    private val savedRecipes : MutableLiveData<List<SavedRecipe>> = MutableLiveData()
    private val savedRecipesList : MutableList<SavedRecipe> = mutableListOf()

    init {
        val listToSubmit = mutableListOf<SavedRecipe>()
        listToSubmit.addAll(savedRecipesList)
        savedRecipes.value = listToSubmit
    }

    fun getSavedRecipes() : LiveData<List<SavedRecipe>> {
        return savedRecipes
    }

}