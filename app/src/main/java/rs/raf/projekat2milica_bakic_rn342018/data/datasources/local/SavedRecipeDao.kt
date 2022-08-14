package rs.raf.projekat2milica_bakic_rn342018.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipeEntity


@Dao
abstract class SavedRecipeDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: SavedRecipeEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<SavedRecipeEntity>): Completable

    @Query("SELECT * FROM saved_recipies")
    abstract fun getAll(): Observable<List<SavedRecipeEntity>>

    @Query("DELETE FROM recipies")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<SavedRecipeEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }


}