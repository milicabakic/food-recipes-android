package rs.raf.projekat2milica_bakic_rn342018.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2milica_bakic_rn342018.data.model.MealEntity

@Dao
abstract class MealDetailsDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: MealEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<MealEntity>): Completable

    @Query("SELECT * FROM recipe_details")
    abstract fun getAll(): Observable<List<MealEntity>>

    @Query("DELETE FROM recipe_details")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entity: MealEntity) {
        deleteAll()
        insert(entity).blockingAwait()
    }

    @Query("SELECT * FROM recipe_details WHERE recipe_id LIKE :id")
    abstract fun getByID(id: String): Observable<MealEntity>

}