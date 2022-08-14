package rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2milica_bakic_rn342018.data.model.Category
import rs.raf.projekat2milica_bakic_rn342018.databinding.LayoutCategoryListItemBinding
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff.CategoryDiffItemCallback
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.viewholder.CategoryViewHolder


class CategoryAdapter(
    categoryDiffItemCallback: CategoryDiffItemCallback,
    private val onCategoryClicked: (Category) -> Unit) : ListAdapter<Category, CategoryViewHolder>(categoryDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = LayoutCategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding){
            val category = getItem(it)
            onCategoryClicked.invoke(category)
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val car = getItem(position)
        holder.bind(car)
    }

}