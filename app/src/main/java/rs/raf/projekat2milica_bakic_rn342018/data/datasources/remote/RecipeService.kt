package rs.raf.projekat2milica_bakic_rn342018.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat2milica_bakic_rn342018.data.model.MealDetailsResponse
import rs.raf.projekat2milica_bakic_rn342018.data.model.RecipesResponse

interface RecipeService {

    @GET("search")
    fun getAllByQ(@Query("q") q : String, @Query("page") page: Int = 1): Observable<RecipesResponse>

    @GET("get")
    fun getRecipeDetailsByID(@Query("rId") id : String): Observable<MealDetailsResponse>

}