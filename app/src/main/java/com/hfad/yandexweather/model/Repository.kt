package com.hfad.yandexweather.model



interface Repository {
    fun getWeatherFromLocal(): Weather

}