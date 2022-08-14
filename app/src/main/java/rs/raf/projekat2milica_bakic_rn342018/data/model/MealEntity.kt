package rs.raf.projekat2milica_bakic_rn342018.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_details")
data class MealEntity (

    @PrimaryKey
    val recipe_id : String,
    val ingredients : List<String>,
    val title : String,
    val image_url : String
)