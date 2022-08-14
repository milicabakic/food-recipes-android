package rs.raf.projekat2milica_bakic_rn342018.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2milica_bakic_rn342018.data.model.RecipeEntity

@Dao
abstract class RecipeDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: RecipeEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<RecipeEntity>): Completable

    @Query("SELECT * FROM recipies")
    abstract fun getAll(): Observable<List<RecipeEntity>>

    @Query("DELETE FROM recipies")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<RecipeEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM recipies WHERE title LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<RecipeEntity>>

}