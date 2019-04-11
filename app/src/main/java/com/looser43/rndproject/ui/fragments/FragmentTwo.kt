package com.looser43.rndproject.ui.fragments


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.looser43.rndproject.R
import com.looser43.rndproject.utils.loadImage
import kotlinx.android.synthetic.main.fragment_fragment_two.*


class FragmentTwo : Fragment() {

    private var GMAP_API_KEY: String? = "AIzaSyAhaboq1z8148-wRHC9MZ-8RpQeC17nuzk"
    private val imgUrl = "https://imgur.com/WSv0lUv"
    private var latitude: Double? = 23.7509425
    private var longitude: Double? = 90.3940483


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_two, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnclicks()
    }

    private fun setOnclicks() {
        btnPlacePicker.setOnClickListener {
            showPlacePicker()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOC_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                showPlacePicker()
            }
        } else if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                val place = PlacePicker.getPlace(data, activity)
                latitude = place.latLng.latitude
                longitude = place.latLng.longitude
                tvName.text = place.name.toString().plus("\nAddress: ").plus(place.address)
                Log.d("mapUrl", "Map URL to be loaded: ${getMapUrl()}")
                context?.loadImage(getMapUrl(), ivMapView)
            }
        }
    }

    private fun showPlacePicker() {
        val latLngBounds = LatLngBounds(
            LatLng(23.7509425, 90.3940483),
            LatLng(23.7509425, 90.3940483)
        )
        val builder = PlacePicker.IntentBuilder()
        builder.setLatLngBounds(latLngBounds)

        try {
            startActivityForResult(builder.build(activity), REQUEST_PLACE_PICKER)
        } catch (e: Exception) {
            Log.e("Map Error", e.stackTrace.toString())
        }

    }

    private fun getMapUrl(): String {
        val iconUrl = "icon:$imgUrl|$latitude,$longitude"

        return "https://maps.googleapis.com/maps/api/staticmap?zoom=15&size=600x400&maptype=roadmap" +
                "&center=" + latitude + "," + longitude + "&markers=" + iconUrl + "%7Clabel:C%7C" + latitude + "," + longitude +
                "&key=" + GMAP_API_KEY /*+ getStaticPath(j)*/
    }


    companion object {
        private val REQUEST_PLACE_PICKER = 100
        private val LOC_REQ_CODE = 1
    }


}
