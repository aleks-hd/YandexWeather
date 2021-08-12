package com.hfad.yandexweather.model

import com.hfad.yandexweather.WeatherDTO

class RepositoryImp: Repository {
    override fun getWeatherFromLocal(): Weather {
        return Weather()
    }

    override fun getWeatherFromServer(): WeatherDTO {
        return WeatherDTO()
    }
}