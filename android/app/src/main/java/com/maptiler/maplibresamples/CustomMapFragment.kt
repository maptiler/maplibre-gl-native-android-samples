package com.maptiler.maplibresamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap

class CustomMapFragment : Fragment() {
    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(context!!, null)
        activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = "Custom Map"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.simple_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create map view
        mapView = view.findViewById(R.id.simpleMapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync {map -> loadStyle(map)}
    }

    private fun loadStyle(map: MapboxMap) {
        val mapTilerKey = Helper.getMapTilerKey(context!!)
        Helper.validateKey(mapTilerKey)

        // Use your custom style url
        val styleUrl = "https://api.maptiler.com/maps/f3c7b19d-7f5b-42a2-8b98-90ed51ca373a/style.json?key=${mapTilerKey}";
        // Set the style after mapView was loaded
        map.setStyle(styleUrl) {
            map.uiSettings.setAttributionMargins(15, 0, 0, 15)
            panToSlopes(map)
        }
    }

    private fun panToSlopes(map: MapboxMap) {
        val latLngBounds = LatLngBounds.Builder()
            .include(LatLng(46.91076825, 10.91279724))
            .include(LatLng(46.98484291, 11.02306368))
            .build()
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 10))
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
    }
}