package rs.raf.projekat2milica_bakic_rn342018.presentation.view.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import rs.raf.projekat2milica_bakic_rn342018.R
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe
import rs.raf.projekat2milica_bakic_rn342018.databinding.ActivityMenuSingleRecipeBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.lang.StringBuilder

class MenuSingleRecipeActivity: AppCompatActivity(R.layout.activity_menu_single_recipe) {

    private lateinit var binding: ActivityMenuSingleRecipeBinding
    private lateinit var savedRecipe: SavedRecipe


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuSingleRecipeBinding.inflate(layoutInflater)
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
        initUI()
    }

    private fun initUI(){
        if(loadImageFromStorage(savedRecipe.image_url, savedRecipe.title)) {
            loadImageFromStorage(savedRecipe.image_url, savedRecipe.title)
        }
        else{
            Glide.with(binding.savedRecipePicture.context).load(savedRecipe.image_url).into(binding.savedRecipePicture)
        }
        binding.savedRecipeTitle.text = savedRecipe.title
        val stringBuilder: StringBuilder = StringBuilder()
        savedRecipe.ingredients.forEach(){
            stringBuilder.append(it+"\n")
        }
        binding.savedRecipeIngredients.text = stringBuilder.toString()
    }

    private fun parseExtra(){
        savedRecipe = intent.getSerializableExtra("savedRecipe") as SavedRecipe
    }

    private fun loadImageFromStorage(path: String, fileName: String): Boolean {
        try{
            val file = File(path, "$fileName.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(file))
            Glide.with(binding.savedRecipePicture.context).load(b).into(binding.savedRecipePicture)
            return true
        } catch (e: FileNotFoundException){
           // e.printStackTrace()
            return false
        }
        return false
    }

}