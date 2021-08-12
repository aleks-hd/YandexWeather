package com.hfad.yandexweather

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.hfad.yandexweather.model.Weather
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collector
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class ReqFromServer {
    private lateinit var weatherFromServer: WeatherDTO


    @RequiresApi(Build.VERSION_CODES.N)
    fun onConnect() {
       try {

        val uri = URL("https://api.weather.yandex.ru/v2/informers?let=53.2000700&lon=50.1500000")
        val handler = Handler()
        Thread {
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.addRequestProperty(
                    "X-Yandex-API-Key",
                    "5682b28c-5134-4a21-ad13-9fb93d2399fd"
                )
                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

                val weatherDTO: WeatherDTO =
                    Gson().fromJson(getLines(bufferedReader), WeatherDTO::class.java)
                handler.post { getWeatherFromServer(weatherDTO) }
            } catch (e:Exception){
                Log.e("", "Error connection",e)
                e.printStackTrace()
            }finally {
                urlConnection.disconnect()
            }
        }.start()
       } catch (e: MalformedURLException){
           Log.e("","Eror url",e)
           e.printStackTrace()
       }
       }

    fun getWeatherFromServer(weatherDTO: WeatherDTO){
        weatherFromServer = weatherDTO
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLines(reader: BufferedReader):String{
        return reader.lines().collect(Collectors.joining("\n"))
    }


}

data class WeatherDTO(
    val fact:FastDTO?
)
data class FastDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?
)
