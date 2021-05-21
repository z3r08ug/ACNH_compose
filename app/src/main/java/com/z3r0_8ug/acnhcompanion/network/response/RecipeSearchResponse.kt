package com.codingwithmitch.acnhcompanion.network.response

import com.codingwithmitch.acnhcompanion.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(

        @SerializedName("count")
        var count: Int,

        @SerializedName("results")
        var recipes: List<RecipeDto>,
)