package com.circus.nativenavs.data

data class ProfileUserReviewDto(
    val imageUrls: List<String>,
    val reviewAverage: Int,
    val reviewCount: Int,
    val reviews: List<Review>,
    val totalImageCount: Int
)