package rs.raf.projekat2milica_bakic_rn342018.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2milica_bakic_rn342018.data.datasources.local.RecipeDatabase
import rs.raf.projekat2milica_bakic_rn342018.data.datasources.remote.RecipeService
import rs.raf.projekat2milica_bakic_rn342018.data.repository.RecipeRepository
import rs.raf.projekat2milica_bakic_rn342018.data.repository.RecipeRepositoryImpl
import rs.raf.projekat2milica_bakic_rn342018.presentation.viewmodel.RecipeViewModel


val recipeModule = module {

    viewModel { RecipeViewModel(recipeRepository = get()) }

    single<RecipeRepository> { RecipeRepositoryImpl(localDataSource = get(), localDataSourceMeal = get(),
            localDataSourceSavedRecipe = get(), remoteDataSource = get()) }

    single { get<RecipeDatabase>().getRecipeDao() }

    single { get<RecipeDatabase>().getMealDetailsDao() }

    single { get<RecipeDatabase>().getSavedRecipeDao() }

    single<RecipeService> { create(retrofit = get()) }

}

