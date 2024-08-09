package com.circus.nativenavs.data

data class ProfileUserReviewDto(
    val imageUrls: List<String>,
    val reviewAverage: Double,
    val reviewCount: Int,
    val reviews: List<Review>,
    val totalImageCount: Int
)