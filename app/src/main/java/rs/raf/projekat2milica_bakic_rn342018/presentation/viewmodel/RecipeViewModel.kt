package rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2milica_bakic_rn342018.data.model.Recipe
import rs.raf.projekat2milica_bakic_rn342018.data.model.Resources
import rs.raf.projekat2milica_bakic_rn342018.data.model.SavedRecipe
import rs.raf.projekat2milica_bakic_rn342018.data.repository.RecipeRepository
import rs.raf.projekat2milica_bakic_rn342018.presentation.contract.RecipeContract
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.AddRecipeState
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.RecipeDetailsState
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.RecipeState
import rs.raf.projekat2milica_bakic_rn342018.presentation.states.SaveRecipeState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RecipeViewModel (
    private val recipeRepository: RecipeRepository
) : ViewModel(), RecipeContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val recipesState: MutableLiveData<RecipeState> = MutableLiveData()
    override val recipeDetailsState: MutableLiveData<RecipeDetailsState> = MutableLiveData()
    override val saveRecipeState: MutableLiveData<SaveRecipeState> = MutableLiveData()
    override val addDone: MutableLiveData<AddRecipeState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                recipeRepository
                    .getAllByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    recipesState.value = RecipeState.Success(it)
                },
                {
                    recipesState.value = RecipeState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllRecipesByQ(q : String) {
        val subscription = recipeRepository
            .fetchAll(q)
            .startWith(Resources.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resources.Loading -> recipesState.value = RecipeState.Loading
                        is Resources.Success -> recipesState.value = RecipeState.DataFetched
                        is Resources.Error -> recipesState.value = RecipeState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    recipesState.value = RecipeState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllRecipes() {
        val subscription = recipeRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    recipesState.value = RecipeState.Success(it)
                },
                {
                    recipesState.value = RecipeState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getRecipesByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun addRecipe(recipe : Recipe) {
        val subscription = recipeRepository
            .insert(recipe)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddRecipeState.Success
                },
                {
                    addDone.value = AddRecipeState.Error("Error happened while adding movie")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchRecipeDetailsByID(id: String) {
        val subscription = recipeRepository
                .fetchRecipeDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            when(it) {
                                is Resources.Success -> recipeDetailsState.value = RecipeDetailsState.DataFetched
                                is Resources.Error -> recipeDetailsState.value = RecipeDetailsState.Error("Error happened while fetching data from the server")
                            }
                        },
                        {
                            recipeDetailsState.value = RecipeDetailsState.Error("Error happened while fetching data from the server")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getRecipeDetailsByID(id: String) {
        val subscription = recipeRepository
                .getRecipeByID(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            recipeDetailsState.value = RecipeDetailsState.Success(it)
                        },
                        {
                            recipeDetailsState.value = RecipeDetailsState.Error("Error happened while adding movie")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getAllSavedRecipes() {
        val subscription = recipeRepository
                .getSavedRecipies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            saveRecipeState.value = SaveRecipeState.Success(it)
                        },
                        {
                            saveRecipeState.value = SaveRecipeState.Error("Error happened while fetching data from db")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun addSavedRecipe(recipeToSave: SavedRecipe) {
        val subscription = recipeRepository
                .insertSavedRecipe(recipeToSave)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            saveRecipeState.value = SaveRecipeState.SuccessfullySaved
                        },
                        {
                            saveRecipeState.value = SaveRecipeState.Error("Error happened while adding movie")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}