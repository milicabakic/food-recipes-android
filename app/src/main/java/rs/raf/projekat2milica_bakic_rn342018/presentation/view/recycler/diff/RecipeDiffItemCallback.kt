package rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2milica_bakic_rn342018.data.model.Recipe

class RecipeDiffItemCallback : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.recipe_id == newItem.recipe_id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.publisher == newItem.publisher &&
                oldItem.image_url == newItem.image_url
    }
}