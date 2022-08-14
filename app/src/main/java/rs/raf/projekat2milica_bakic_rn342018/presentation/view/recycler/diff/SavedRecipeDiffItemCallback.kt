package rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe

class SavedRecipeDiffItemCallback : DiffUtil.ItemCallback<SavedRecipe>() {

    override fun areItemsTheSame(oldItem: SavedRecipe, newItem: SavedRecipe): Boolean {
        return oldItem.recipe_id == newItem.recipe_id
    }

    override fun areContentsTheSame(oldItem: SavedRecipe, newItem: SavedRecipe): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.date == oldItem.date &&
                oldItem.image_url == oldItem.image_url
    }
}