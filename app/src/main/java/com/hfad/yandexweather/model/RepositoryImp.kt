package com.hfad.yandexweather.model


class RepositoryImp: Repository {
    override fun getWeatherFromLocal(): Weather {
        return Weather()
    }


}