package com.example.location_detector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.location_detector.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var stationName:String?=null
    private lateinit var locationText:TextView
    private lateinit var refreshbtn:Button
    private var trainStations = mutableListOf<Location>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        locationText = binding.showLocation
        refreshbtn = binding.button
        if (savedInstanceState != null) {
            // Activity was recreated, restore the selected station
            stationName = savedInstanceState.getString("selected_station")
            locationText.text = stationName
        }
        val apiHelper = LocationApiHelperImpl(RetrofitBuilder.apiService)
        val locationviewmodel = ViewModelProvider(this,MyViewModelFactory(apiHelper)).get(LocationViewModel::class.java)
        locationviewmodel.locationList.observe(this) {
            trainStations.clear()
            trainStations.addAll(it)
        }
        locationText.setOnClickListener {
            showLocationPopup(locationText)
        }
        refreshbtn.setOnClickListener {
            locationviewmodel.refresh()
        }
    }

    private fun showLocationPopup(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.location, popupMenu.menu)
        for (i in trainStations.indices) {
            popupMenu.menu?.add(0, i, i, "${trainStations[i].name}-${trainStations[i].state}")
        }
        popupMenu.setOnMenuItemClickListener { menuItem ->
             stationName = menuItem.title as String
            locationText.text=stationName
            true
        }
        popupMenu.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("selected_station", stationName)
    }

    }