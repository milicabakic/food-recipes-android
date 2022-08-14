package rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.projekat2milica_bakic_rn342018.data.model.Recipe
import rs.raf.projekat2milica_bakic_rn342018.databinding.LayoutCategoryListItemBinding
import rs.raf.projekat2milica_bakic_rn342018.databinding.LayoutRecipeItemBinding

class RecipeViewHolder (
        private val itemBinding: LayoutRecipeItemBinding,
        onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(itemBinding.root) {

    init {

        itemBinding.recipePicture.setOnClickListener {
            onItemClicked.invoke(adapterPosition)
        }
    }

    fun bind(recipe : Recipe) {
        Glide.with(itemBinding.recipePicture.context).load(recipe.image_url).circleCrop().into(itemBinding.recipePicture)
        itemBinding.recipeTitle.text = recipe.title
        itemBinding.recipePublisher.text = recipe.publisher
    }
}