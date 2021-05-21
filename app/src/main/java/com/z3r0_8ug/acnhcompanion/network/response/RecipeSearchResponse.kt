package com.z3r0_8ug.acnhcompanion.network.response

import com.z3r0_8ug.acnhcompanion.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(

        @SerializedName("count")
        var count: Int,

        @SerializedName("results")
        var recipes: List<RecipeDto>,
)