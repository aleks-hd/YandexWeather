package com.hfad.yandexweather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.hfad.yandexweather.ReqFromServer
import com.hfad.yandexweather.viewmodel.AppState
import com.hfad.yandexweather.viewmodel.MainViewModel
import com.hfad.yandexweather.databinding.FragmentMainBinding


class FragmentMain : Fragment() {

    lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
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
            ReqFromServer().onConnect()

        }
    }

    private fun render(data: AppState) {
        when(data){
            is AppState.Success -> {
                val weatherData = data.weather
                binding.nameCity.text = weatherData.nameCity.toString()
                binding.temperature.text = weatherData.temperature.toString()
                binding.lar.text = weatherData.lar.toString()
                binding.lat.text = weatherData.lat.toString()
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root,"Success",Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading ->{
                binding.loadingLayout.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root,"error",Snackbar.LENGTH_LONG).show()
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