package rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe
import rs.raf.projekat2milica_bakic_rn342018.databinding.LayoutSavedRecipeItemBinding

class SavedRecipeViewHolder (
        private val itemBinding: LayoutSavedRecipeItemBinding,
        onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(itemBinding.root) {

    init {

        itemBinding.savedRecipeID.setOnClickListener{
            onItemClicked.invoke(adapterPosition)
        }

    }

    fun bind(recipe : SavedRecipe) {
        itemBinding.savedTitle.text = recipe.title
        itemBinding.savedCategory.text = recipe.category
        itemBinding.savedDate.text = recipe.date.toString()
    }
}