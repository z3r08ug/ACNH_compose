package com.z3r0_8ug.acnhcompanion.di

import com.z3r0_8ug.food2fork.network.RecipeService
import com.z3r0_8ug.acnhcompanion.cache.RecipeDao
import com.z3r0_8ug.acnhcompanion.cache.model.RecipeEntityMapper
import com.z3r0_8ug.acnhcompanion.interactors.recipe.GetRecipe
import com.z3r0_8ug.acnhcompanion.interactors.recipe_list.RestoreRecipes
import com.z3r0_8ug.acnhcompanion.interactors.recipe_list.SearchRecipes
import com.z3r0_8ug.acnhcompanion.network.model.RecipeDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

  @ViewModelScoped
  @Provides
  fun provideSearchRecipe(
    recipeService: RecipeService,
    recipeDao: RecipeDao,
    recipeEntityMapper: RecipeEntityMapper,
    recipeDtoMapper: RecipeDtoMapper,
  ): SearchRecipes {
    return SearchRecipes(
      recipeService = recipeService,
      recipeDao = recipeDao,
      entityMapper = recipeEntityMapper,
      dtoMapper = recipeDtoMapper,
    )
  }

  @ViewModelScoped
  @Provides
  fun provideRestoreRecipes(
    recipeDao: RecipeDao,
    recipeEntityMapper: RecipeEntityMapper,
  ): RestoreRecipes {
    return RestoreRecipes(
      recipeDao = recipeDao,
      entityMapper = recipeEntityMapper,
    )
  }

  @ViewModelScoped
  @Provides
  fun provideGetRecipe(
    recipeDao: RecipeDao,
    recipeEntityMapper: RecipeEntityMapper,
    recipeService: RecipeService,
    recipeDtoMapper: RecipeDtoMapper,
  ): GetRecipe {
    return GetRecipe(
      recipeDao = recipeDao,
      entityMapper = recipeEntityMapper,
      recipeService = recipeService,
      recipeDtoMapper = recipeDtoMapper,
    )
  }

}











