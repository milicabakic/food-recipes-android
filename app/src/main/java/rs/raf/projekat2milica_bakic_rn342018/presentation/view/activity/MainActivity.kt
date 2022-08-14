package rs.raf.projekat2milica_bakic_rn342018.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_main.*
import rs.raf.projekat2milica_bakic_rn342018.R
import rs.raf.projekat2milica_bakic_rn342018.data.model.Meal
import rs.raf.projekat2milica_bakic_rn342018.databinding.ActivityMainBinding
import rs.raf.projekat2milica_bakic_rn342018.presentation.contract.RecipeContract
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.RecipeState
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.adapter.RecipeAdapter
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff.CategoryDiffItemCallback
import rs.raf.projekat2milica_bakic_rn342018.presentation.view.recycler.diff.RecipeDiffItemCallback
import rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel.RecipeViewModel
import rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel.RecyclerViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val recyclerViewModel: RecyclerViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var searchView: SearchView

    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mainViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()

    private var _binding: ActivityMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
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
                binding.recipesRecycler.isVisible = false
                binding.listRecycler.isVisible = true
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

    private fun init() {
        initUi()
        initListeners()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        searchView = findViewById(R.id.searchView)
    }

    private fun initRecycler() {
        listRecycler.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter(CategoryDiffItemCallback()) {
            //Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            // Ako hocemo da otvorimo novi activity klikom na item u listi
            // to radimo ovde, ne prosledjujemo context u listadapter ili viewholder
            // val intent = Intent(this, LoginActivity::class.java)
            //startActivity(intent)
            mainViewModel.getAllRecipes()
            mainViewModel.fetchAllRecipesByQ(it.name)
            listRecycler.isVisible = false
        }
        listRecycler.adapter = categoryAdapter

        binding.recipesRecycler.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter(RecipeDiffItemCallback()){
            //mainViewModel.getRecipeDetailsByID(it.recipe_id)
           // mainViewModel.fetchRecipeDetailsByID(it.recipe_id)

            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra("recipe_id", it.recipe_id)
            startActivity(intent)
        }
        binding.recipesRecycler.adapter = recipeAdapter
        binding.recipesRecycler.isVisible = false
    }

    private fun initListeners() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getAllRecipes()
                mainViewModel.fetchAllRecipesByQ(query)
                listRecycler.isVisible = false
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
               // recyclerViewModel.filterCategories(newText)
                return false
            }
        })

    }

    private fun initObservers() {
        recyclerViewModel.getCategories().observe(this, Observer {
            categoryAdapter.submitList(it)
        })

        mainViewModel.recipesState.observe(this, Observer {
            Timber.e(it.toString())
            renderState(it)
        })


    }

    private fun renderState(state: RecipeState) {
        when (state) {
            is RecipeState.Success -> {
                showLoadingState(false)
                recipeAdapter.submitList(state.recipes)
            }
            is RecipeState.Error -> {
                showLoadingState(false)
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is RecipeState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is RecipeState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.recipesRecycler.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

}