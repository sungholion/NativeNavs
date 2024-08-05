package com.circus.nativenavs.data

data class CategoryDto(
    val id: Int,
    val name: String,
    val image: String,
    val englishName: String,
    var isChecked: Boolean = false
) {
}