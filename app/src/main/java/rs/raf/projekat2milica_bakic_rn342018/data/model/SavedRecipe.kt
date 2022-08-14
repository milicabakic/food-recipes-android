package rs.raf.projekat2milica_bakic_rn342018.data.model

import java.io.Serializable
import java.util.*

data class SavedRecipe (
        val recipe_id : String,
        val ingredients : List<String>,
        val date : Date,
        val title : String,
        val image_url : String,
        val category: String
): Serializable