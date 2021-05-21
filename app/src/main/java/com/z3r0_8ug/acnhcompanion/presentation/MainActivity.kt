package com.codingwithmitch.acnhcompanion.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.codingwithmitch.acnhcompanion.datastore.SettingsDataStore
import com.codingwithmitch.acnhcompanion.presentation.navigation.Screen
import com.codingwithmitch.acnhcompanion.presentation.ui.recipe.RecipeDetailScreen
import com.codingwithmitch.acnhcompanion.presentation.ui.recipe.RecipeViewModel
import com.codingwithmitch.acnhcompanion.presentation.ui.recipe_list.RecipeListScreen
import com.codingwithmitch.acnhcompanion.presentation.ui.recipe_list.RecipeListViewModel
import com.codingwithmitch.acnhcompanion.presentation.util.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

  @Inject
  lateinit var connectivityManager: ConnectivityManager

  @Inject
  lateinit var settingsDataStore: SettingsDataStore

  override fun onStart() {
    super.onStart()
    connectivityManager.registerConnectionObserver(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    connectivityManager.unregisterConnectionObserver(this)
  }

  @ExperimentalComposeUiApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.RecipeList.route) {
          composable(route = Screen.RecipeList.route) { navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: RecipeListViewModel = viewModel("RecipeListViewModel", factory)
            RecipeListScreen(
              isDarkTheme = settingsDataStore.isDark.value,
              isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
              onToggleTheme = settingsDataStore::toggleTheme,
              onNavigateToRecipeDetailScreen = navController::navigate,
              viewModel = viewModel,
            )
          }
          composable(
            route = Screen.RecipeDetail.route + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") {
              type = NavType.IntType
            })
          ) { navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: RecipeViewModel = viewModel("RecipeDetailViewModel", factory)
            RecipeDetailScreen(
              isDarkTheme = settingsDataStore.isDark.value,
              isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
              recipeId = navBackStackEntry.arguments?.getInt("recipeId"),
              viewModel = viewModel,
            )
          }
        }

    }
  }


}














