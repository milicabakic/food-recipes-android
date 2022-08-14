package rs.raf.projekat2milica_bakic_rn342018.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2milica_bakic_rn342018.R
import rs.raf.projekat2milica_bakic_rn342018.databinding.ActivityMenuBinding
import rs.raf.projekat2milica_bakic_rn342018.presentation.contract.RecipeContract
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.SaveRecipeState
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.adapter.SavedRecipeAdapter
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff.SavedRecipeDiffItemCallback
import rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel.RecipeViewModel
import rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel.SavedRecipesViewModel
import timber.log.Timber

class MenuActivity : AppCompatActivity(R.layout.activity_menu) {

    private val mainViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var binding: ActivityMenuBinding

    private val savedRecipesViewModel: SavedRecipesViewModel by viewModels()
    private lateinit var savedRecipeAdapter: SavedRecipeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
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
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init(){
        mainViewModel.getAllSavedRecipes()
        initUI()
        initObservers()
        initListeners()
    }

    private fun initUI(){
        initRecycler()
    }

    private fun initRecycler(){
        binding.listSavedRecipes.layoutManager = LinearLayoutManager(this)
        savedRecipeAdapter = SavedRecipeAdapter(SavedRecipeDiffItemCallback()){
            val intent = Intent(this,MenuSingleRecipeActivity::class.java)
            intent.putExtra("savedRecipe", it)
            startActivity(intent)
        }
        binding.listSavedRecipes.adapter = savedRecipeAdapter
    }

    private fun initObservers(){
        savedRecipesViewModel.getSavedRecipes().observe(this, Observer {
            savedRecipeAdapter.submitList(it)
        })

        mainViewModel.saveRecipeState.observe(this, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }

    private fun renderState(state: SaveRecipeState){
        when (state) {
            is SaveRecipeState.Success -> {
                savedRecipeAdapter.submitList(state.recipes)
            }
            is SaveRecipeState.Error -> {
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is SaveRecipeState.DataFetched -> {
                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SaveRecipeState.SuccessfullySaved -> {
                Toast.makeText(this, "Recipe is successfully added to menu", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initListeners(){

    }

}