package com.z3r0_8ug.acnhcompanion.presentation.ui.recipe_list

sealed class RecipeListEvent {

    object NewSearchEvent : RecipeListEvent()

    object NextPageEvent : RecipeListEvent()

    // restore after process death
    object RestoreStateEvent: RecipeListEvent()
}