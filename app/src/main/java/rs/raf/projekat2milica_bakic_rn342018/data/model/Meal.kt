package rs.raf.projekat2milica_bakic_rn342018.data.model

import java.io.Serializable


data class Meal (
        val recipe_id : String,
        val ingredients : List<String>,
        val title : String,
        var image_url : String
): Serializable