package rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe
import rs.raf.projekat2milica_bakic_rn342018.databinding.LayoutSavedRecipeItemBinding
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff.SavedRecipeDiffItemCallback
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.viewholder.SavedRecipeViewHolder

class SavedRecipeAdapter (
        savedRecipeDiffItemCallback: SavedRecipeDiffItemCallback,
        private val onRecipeClicked: (SavedRecipe) -> Unit) : ListAdapter<SavedRecipe, SavedRecipeViewHolder>(savedRecipeDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipeViewHolder {
        val itemBinding = LayoutSavedRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedRecipeViewHolder(itemBinding){
            val savedRecipe = getItem(it)
            onRecipeClicked.invoke(savedRecipe)
        }
    }

    override fun onBindViewHolder(holder: SavedRecipeViewHolder, position: Int) {
        val savedRecipe = getItem(position)
        holder.bind(savedRecipe)
    }

}