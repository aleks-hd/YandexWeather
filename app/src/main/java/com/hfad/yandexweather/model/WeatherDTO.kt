package com.hfad.yandexweather.model

data class WeatherDTO(
val fact:FastDTO?
)
data class FastDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?,
    val icon:String?
)

