package com.circus.nativenavs.data

data class Review(
    val createdAt: String,
    val description: String,
    val id: Int,
    val imageUrls: List<String>,
    val reviewer: Reviewer,
    val score: Double,
    val tourTitle: String
)