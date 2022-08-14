package rs.raf.projekat2milica_bakic_rn342018.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipies")
data class RecipeEntity (

   @PrimaryKey
   val recipe_id : String,
   val title : String,
   val image_url : String,
   val publisher : String
)