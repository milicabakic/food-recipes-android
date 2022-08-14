package rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rs.raf.projekat2milica_bakic_rn342018.data.model.Category
import kotlin.random.Random

class RecyclerViewModel : ViewModel() {

    private val categories : MutableLiveData<List<Category>> = MutableLiveData()
    private val categoryList : MutableList<Category> = mutableListOf()

    init {
        var cnt : Int = 1
        val category1 = Category(cnt++, "Barbeque", "https://www.licious.in/blog/wp-content/uploads/2020/12/Chicken-Barbeque-Kebab.jpg")
        val category2 = Category(cnt++, "Breakfast", "https://iamafoodblog.b-cdn.net/wp-content/uploads/2019/02/full-english-7355w-2.jpg")
        val category3 = Category(cnt++, "Chicken", "https://media-cldnry.s-nbcnews.com/image/upload/newscms/2019_20/1438251/rotisserie-chicken-today-main-190517-1438251.jpg")
        val category4 = Category(cnt++, "Beef", "https://challengedairy.com/sites/default/files/recipe/images/recipe_roast_beef_with_tangy_mustard_sauce_2280.jpg")
        val category5 = Category(cnt++, "Brunch", "https://cdn.vox-cdn.com/thumbor/gr2BKoy2_YZ08co1NHg7-G9wFbA=/0x0:1800x1796/1200x900/filters:focal(756x754:1044x1042)/cdn.vox-cdn.com/uploads/chorus_image/image/62872809/son_and_garden.62.jpg")
        val category6 = Category(cnt++, "Dinner", "https://static.toiimg.com/thumb/msid-46173052,width-800,height-600,resizemode-75,imgsize-92109/46173052.jpg")
        val category7 = Category(cnt++, "Wine", "https://www.morethanbelgrade.com/wp-content/uploads/2021/01/wine-Serbia.jpg")
        val category8 = Category(cnt++, "Italian", "https://assets.site-static.com/userFiles/1621/image/italian-food-in-durango.jpg")

        categoryList.add(category1)
        categoryList.add(category2)
        categoryList.add(category3)
        categoryList.add(category4)
        categoryList.add(category5)
        categoryList.add(category6)
        categoryList.add(category7)
        categoryList.add(category8)

/*
        for (i in 1..8) {
            val category = Category(
                  i,
                "Category " + i,
                "https://www.linguahouse.com/linguafiles/md5/d01dfa8621f83289155a3be0970fb0cb"
            )
            categoryList.add(category)
        } */

        val listToSubmit = mutableListOf<Category>()
        listToSubmit.addAll(categoryList)
        categories.value = listToSubmit
    }

    fun getCategories() : LiveData<List<Category>> {
        return categories
    }

    fun filterCategories(filter: String) {
        val filteredList = categoryList.filter {
            it.name.toLowerCase().startsWith(filter.toLowerCase())
        }
        categories.value = filteredList
    }


}