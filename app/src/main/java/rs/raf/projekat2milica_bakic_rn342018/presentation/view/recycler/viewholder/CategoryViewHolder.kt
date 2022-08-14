package rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.projekat2milica_bakic_rn342018.data.model.Category
import rs.raf.projekat2milica_bakic_rn342018.databinding.LayoutCategoryListItemBinding

class CategoryViewHolder(
    private val itemBinding: LayoutCategoryListItemBinding,
    onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        // Kada dodamo click listener na containerView to znaci da ce biti registrovan klik
        // na bilo koji deo itema u listi
//        itemBinding.setOnClickListener {
//            onItemClicked.invoke(adapterPosition)
//        }
        // Kada hocemo da registrujemo klik samo na odredjenoj komponenti u itemu,
        // dodajemo click listener bas na tu komponentu
        itemBinding.carPictureIv.setOnClickListener {
            onItemClicked.invoke(adapterPosition)
        }
    }

    fun bind(category: Category) {
        Glide.with(itemBinding.carPictureIv.context).load(category.photoURL).circleCrop().into(itemBinding.carPictureIv)
        itemBinding.manufacturerTv.text = category.name
    }
}