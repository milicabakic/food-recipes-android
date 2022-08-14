package rs.raf.projekat2milica_bakic_rn342018.data.model

data class MealResponse (
    val recipe_id : String,
    val ingredients : List<String>,
    val title : String,
    val image_url : String
)