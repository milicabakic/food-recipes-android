package rs.raf.projekat2milica_bakic_rn342018.presentation.view.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2milica_bakic_rn342018.R
import rs.raf.projekat2milica_bakic_rn342018.data.model.Meal
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe
import rs.raf.projekat2milica_bakic_rn342018.databinding.ActivitySaverecipeBinding
import rs.raf.projekat2milica_bakic_rn342018.presentation.contract.RecipeContract
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.SaveRecipeState
import rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel.RecipeViewModel
import timber.log.Timber
import java.io.*
import java.lang.Exception
import java.util.*


class SaveRecipeActivity : AppCompatActivity(R.layout.activity_saverecipe){

    private lateinit var binding: ActivitySaverecipeBinding
    private lateinit var mealToSave: Meal
    private val mainViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()

    private var photoTaken: Boolean = false
    private var pathToRemember: String = ""
    private lateinit var alertDialog : AlertDialog.Builder
    private val getPicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val bundle = it.data?.extras
            val finalPhoto: Bitmap = bundle?.get("data") as Bitmap
          //  Glide.with(binding.recipePicture.context).load(finalPhoto).into(binding.recipePicture)

            val path: String? = saveToInternalStorage(finalPhoto, mealToSave.title)
            if (path != null) {
                loadImageFromStorage(path, mealToSave.title)
            }
            photoTaken = true
            pathToRemember = path.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaverecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseExtra()
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
        initUI()
        initDialog()
        initListeners()
        initObservers()
    }

    private fun initUI(){
        binding.recipeTitle.text = mealToSave.title
        Glide.with(binding.recipePicture.context).load(mealToSave.image_url).into(binding.recipePicture)
    }

    private fun initDialog(){
        alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Allow FoodRecipes to open Camera?")

        alertDialog.setPositiveButton(R.string.dialogYes) { dialog, which ->
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try{
                getPicture.launch(takePictureIntent)
            }
            catch (exception: ActivityNotFoundException){
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialog.setNegativeButton(R.string.dialogNo) { dialog, which ->
        }

    }

    private fun initListeners(){
        binding.saveRecipeBtn.setOnClickListener {
            //set date
            var dateToSave = Date(binding.datePicker1.year - 1900, binding.datePicker1.month, binding.datePicker1.dayOfMonth)

            lateinit var recipeToSave: SavedRecipe
            if(photoTaken){
                recipeToSave = SavedRecipe(mealToSave.recipe_id, mealToSave.ingredients, dateToSave, mealToSave.title, pathToRemember,
                        binding.spinner.getSelectedItem().toString())

            } else{
                recipeToSave = SavedRecipe(mealToSave.recipe_id, mealToSave.ingredients, dateToSave, mealToSave.title, mealToSave.image_url,
                        binding.spinner.getSelectedItem().toString())
            }


            mainViewModel.addSavedRecipe(recipeToSave)
        }

        binding.recipePicture.setOnClickListener {
            alertDialog.show()
        }
    }

    private fun initObservers(){
        mainViewModel.saveRecipeState.observe(this, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }

    private fun parseExtra(){
        mealToSave = intent.getSerializableExtra("meal") as Meal
    }

    private fun renderState(state: SaveRecipeState){
        when (state) {
            is SaveRecipeState.Success -> {
               // recipeAdapter.submitList(state.recipes)
                println("SUCCESS IN SAVE RECIPE STATE")
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


    private fun saveToInternalStorage(bitmapImage: Bitmap, fileName: String): String? {
        val cw = ContextWrapper(applicationContext)
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val myPath = File(directory, "$fileName.jpg")
        var fos: FileOutputStream? = null

        try{
            fos = FileOutputStream(myPath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch(e: Exception){
            e.printStackTrace()
        } finally {
            try{
                fos?.close()
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    private fun loadImageFromStorage(path: String, fileName: String) {
        try{
            val file = File(path, "$fileName.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(file))
            Glide.with(binding.recipePicture.context).load(b).into(binding.recipePicture)
        } catch (e: FileNotFoundException){
            e.printStackTrace()
        }
    }



}