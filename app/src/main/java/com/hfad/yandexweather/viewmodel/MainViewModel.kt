package com.hfad.yandexweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.yandexweather.model.RepositoryImp
import java.lang.Thread.sleep

class MainViewModel (private val repositoryImp: RepositoryImp = RepositoryImp(),
                     private val liveDataToObserver:MutableLiveData<AppState> = MutableLiveData()):ViewModel() {

    fun getData():LiveData<AppState>{
        return liveDataToObserver
    }

  fun getWeather() = getDataLocalSource()

    private fun getDataLocalSource(){
        Thread{
            sleep(1000)
            liveDataToObserver.postValue(AppState.Success(repositoryImp.getWeatherFromLocal()))
        }.start()
    }
}