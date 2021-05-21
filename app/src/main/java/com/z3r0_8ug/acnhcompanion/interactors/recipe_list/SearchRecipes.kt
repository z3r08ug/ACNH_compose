package com.codingwithmitch.acnhcompanion.interactors.recipe_list

import com.codingwithmitch.food2fork.network.RecipeService
import com.codingwithmitch.acnhcompanion.cache.RecipeDao
import com.codingwithmitch.acnhcompanion.cache.model.RecipeEntityMapper
import com.codingwithmitch.acnhcompanion.domain.data.DataState
import com.codingwithmitch.acnhcompanion.domain.model.Recipe
import com.codingwithmitch.acnhcompanion.network.model.RecipeDtoMapper
import com.codingwithmitch.acnhcompanion.util.RECIPE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipes(
  private val recipeDao: RecipeDao,
  private val recipeService: RecipeService,
  private val entityMapper: RecipeEntityMapper,
  private val dtoMapper: RecipeDtoMapper,
) {

  fun execute(
      token: String,
      page: Int,
      query: String,
      isNetworkAvailable: Boolean,
  ): Flow<DataState<List<Recipe>>> = flow {
    try {
      emit(DataState.loading())

      // just to show pagination, api is fast
      delay(1000)

      // force error for testing
      if (query == "error") {
          throw Exception("Search FAILED!")
      }

      // if there is a network connection
      if(isNetworkAvailable){
        // Convert: NetworkRecipeEntity -> Recipe -> RecipeCacheEntity
        val recipes = getRecipesFromNetwork(
          token = token,
          page = page,
          query = query,
        )

        // insert into cache
        recipeDao.insertRecipes(entityMapper.toEntityList(recipes))
      }

      // query the cache
      val cacheResult = if (query.isBlank()) {
        recipeDao.getAllRecipes(
            pageSize = RECIPE_PAGINATION_PAGE_SIZE,
            page = page
        )
      } else {
        recipeDao.searchRecipes(
            query = query,
            pageSize = RECIPE_PAGINATION_PAGE_SIZE,
            page = page
        )
      }

      // emit List<Recipe> from cache
      val list = entityMapper.fromEntityList(cacheResult)

      emit(DataState.success(list))
    } catch (e: Exception) {
      emit(DataState.error<List<Recipe>>(e.message ?: "Unknown Error"))
    }
  }

  // WARNING: This will throw exception if there is no network connection
  private suspend fun getRecipesFromNetwork(
      token: String,
      page: Int,
      query: String
  ): List<Recipe> {
    return dtoMapper.toDomainList(
        recipeService.search(
            token = token,
            page = page,
            query = query,
        ).recipes
    )
  }
}