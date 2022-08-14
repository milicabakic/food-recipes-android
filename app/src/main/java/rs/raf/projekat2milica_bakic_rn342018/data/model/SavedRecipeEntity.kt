package rs.raf.projekat2milica_bakic_rn342018.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "saved_recipies")
data class SavedRecipeEntity (

        @PrimaryKey
        val recipe_id : String,
        val ingredients : List<String>,
        val date : Date,
        val title : String,
        val image_url : String,
        val category: String
)