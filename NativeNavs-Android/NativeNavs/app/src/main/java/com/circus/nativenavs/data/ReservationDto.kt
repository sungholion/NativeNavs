package com.circus.nativenavs.data

data class ReservationDto(
    val date: String,
    val reservationId: Int,
    val reviewed: Boolean,
    val status: String,
    val tourId: Int
)