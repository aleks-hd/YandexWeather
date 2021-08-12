package com.hfad.yandexweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.yandexweather.R


class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
    }

    private fun initFragment() {
       supportFragmentManager.beginTransaction()
               .replace(R.id.fragmentContainer, FragmentMain.newInstance())
           .commit()
    }


}