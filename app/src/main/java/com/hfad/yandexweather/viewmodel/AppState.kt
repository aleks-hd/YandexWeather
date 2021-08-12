package com.hfad.yandexweather.viewmodel

import com.hfad.yandexweather.WeatherDTO
import com.hfad.yandexweather.model.Weather

sealed class AppState{
    data class SuccessDTO(val weatherdto: WeatherDTO): AppState()
    data class Success(val weather: Weather): AppState()
    data class Error(val error:Throwable): AppState()
    object Loading: AppState()
}
