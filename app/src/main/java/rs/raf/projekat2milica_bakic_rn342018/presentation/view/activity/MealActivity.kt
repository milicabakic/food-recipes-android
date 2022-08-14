package rs.raf.projekat2milica_bakic_rn342018.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2milica_bakic_rn342018.R
import rs.raf.projekat2milica_bakic_rn342018.data.model.Meal
import rs.raf.projekat2milica_bakic_rn342018.databinding.ActivityMealBinding
import rs.raf.projekat2milica_bakic_rn342018.presentation.contract.RecipeContract
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.RecipeDetailsState
import rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel.RecipeViewModel
import timber.log.Timber
import java.lang.StringBuilder
import java.util.*

class MealActivity : AppCompatActivity(R.layout.activity_meal) {

    private lateinit var binding: ActivityMealBinding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val mainViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var shownMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_item1 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_item2 -> {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init(){
        mainViewModel.getRecipeDetailsByID(intent.getStringExtra("recipe_id").toString())
        mainViewModel.fetchRecipeDetailsByID(intent.getStringExtra("recipe_id").toString())

        initUI()
        initObservers()
        initListners()
    }

    private fun initUI(){

    }

    private fun initObservers(){
        mainViewModel.recipeDetailsState.observe(this, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }

    private fun initListners(){
        binding.saveRecipeBtn.setOnClickListener {
            val intent = Intent(this,SaveRecipeActivity::class.java)
            intent.putExtra("meal", shownMeal)
            startActivity(intent)
        }
    }

    private fun renderState(state: RecipeDetailsState) {
        when (state) {
            is RecipeDetailsState.Success -> {
                shownMeal = state.meal
                enterValues(state.meal)
            }
            is RecipeDetailsState.Error -> {
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is RecipeDetailsState.DataFetched -> {
                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun enterValues(meal: Meal){
        Glide.with(binding.mealPicture.context).load(meal.image_url).into(binding.mealPicture)
        binding.mealTitle.text = meal.title
        val stringBuilder: StringBuilder = StringBuilder()
        meal.ingredients.forEach(){
            stringBuilder.append(it+"\n")
        }
        binding.mealIngredients.text = stringBuilder.toString()
    }

}