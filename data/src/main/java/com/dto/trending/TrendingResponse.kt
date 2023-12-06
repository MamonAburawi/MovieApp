package com.dto.trending

import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    val page: Int,
    val results: List<TrendingDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)