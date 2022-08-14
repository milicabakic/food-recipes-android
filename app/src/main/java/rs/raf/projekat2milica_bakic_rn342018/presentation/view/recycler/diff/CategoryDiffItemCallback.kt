package rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2milica_bakic_rn342018.data.model.Category

class CategoryDiffItemCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return  oldItem.name == newItem.name &&
                oldItem.photoURL == newItem.photoURL
    }

}