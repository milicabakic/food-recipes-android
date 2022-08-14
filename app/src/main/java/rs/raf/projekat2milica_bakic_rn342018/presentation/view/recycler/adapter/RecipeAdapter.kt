package rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2milica_bakic_rn342018.data.model.Category
import rs.raf.projekat2milica_bakic_rn342018.data.model.Recipe
import rs.raf.projekat2milica_bakic_rn342018.databinding.LayoutRecipeItemBinding
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff.CategoryDiffItemCallback
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff.RecipeDiffItemCallback
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.viewholder.CategoryViewHolder
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.viewholder.RecipeViewHolder

class RecipeAdapter (
        recipeDiffItemCallback: RecipeDiffItemCallback,
        private val onRecipeClicked: (Recipe) -> Unit) : ListAdapter<Recipe, RecipeViewHolder>(recipeDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding = LayoutRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding){
            val recipe = getItem(it)
            onRecipeClicked.invoke(recipe)
        }
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}