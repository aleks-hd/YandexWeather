package com.hfad.yandexweather.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.hfad.yandexweather.ReqFromServer
import com.hfad.yandexweather.databinding.FragmentMainBinding
import com.hfad.yandexweather.model.WeatherDTO
import com.hfad.yandexweather.viewmodel.AppState
import com.hfad.yandexweather.viewmodel.MainViewModel


class FragmentMain : Fragment() {

    lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val onLoadListener: ReqFromServer.WeatherLoader = object : ReqFromServer.WeatherLoader {
        override fun onLoaded(weatherDTO: WeatherDTO) {
            renderDTO(weatherDTO)
        }

        override fun onFailed(throwable: Throwable) {
            Toast.makeText(context,"Ошибка загрузки данных", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            render(it)
        })
        viewModel.getWeather()
        binding.submitWeather.setOnClickListener {
            var latEditText: String = binding.latEdit.text.toString()
            var lonEditText: String = binding.lonEdit.text.toString()
            val loader = ReqFromServer(onLoadListener, latEditText, lonEditText)
            loader.onConnect()

        }
    }

    private fun renderDTO(dataDTO: WeatherDTO) {
        with(binding) {
            temp.text = "Текущая температура (°C) = " + dataDTO.fact?.temp.toString()
            feelsLike.text = "Ощущаемая температура (°C) = " + dataDTO.fact?.feels_like.toString()
            condition.text = "Погода" + dataDTO.fact?.condition.toString()
            val icon: String = dataDTO.fact?.icon.toString()
            /* Glade.with(binding.root.context)
                 .load("https://yastatic.net/weather/i/icons/funky/dark/${icon}.svg")
                 .into(binding.iconWeather)*/
        }
    }

    private fun render(data: AppState) {
        when (data) {
            is AppState.Success -> {
                val weatherData = data.weather
                binding.nameCity.text = weatherData.nameCity.toString()
                binding.temperature.text = weatherData.temperature.toString()
                binding.lar.text = weatherData.lar.toString()
                binding.lat.text = weatherData.lat.toString()
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun newInstance() = FragmentMain()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}