package com.maptiler.maplibresamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils


class AnnotationFragment : Fragment() {

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(context!!, null)
        activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
            "Annotation"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.annotation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapTilerKey = Helper.getMapTilerKey(context!!)
        Helper.validateKey(mapTilerKey)
        val styleUrl = "https://api.maptiler.com/maps/streets/style.json?key=${mapTilerKey}";

        // Create map view
        mapView = view.findViewById(R.id.annotationMapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->

            // Navigate the map to the area with icon
            map.cameraPosition = CameraPosition.Builder()
                .target(LatLng(6.687337, 0.381457))
                .zoom(11.0)
                .build()

            // Load the style and add icon after style has been loaded
            map.setStyle(styleUrl) {
                addSymbolAnnotation(mapView!!, map, it)
            }
        }
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

    private fun addSymbolAnnotation(mapView: MapView, mapboxMap: MapboxMap, style: Style) {

        // Add icon to the style
        addAirplaneImageToStyle(style);

        // Create a SymbolManager.
        val symbolManager = SymbolManager(mapView, mapboxMap, style)

        // Set non-data-driven properties.
        symbolManager.iconAllowOverlap = true
        symbolManager.iconIgnorePlacement = true

        // Create a symbol at the specified location.
        val symbolOptions = SymbolOptions()
            .withLatLng(LatLng(6.687337, 0.381457))
            .withIconImage("airport")
            .withIconSize(1.3f)

        // Use the manager to draw the annotations.
        symbolManager.create(symbolOptions)
    }

    private fun addAirplaneImageToStyle(style: Style) {
        style.addImage(
            "airport",
            BitmapUtils.getBitmapFromDrawable(resources.getDrawable(R.drawable.ic_airplanemode_active_black_24dp))!!,
            true
        )
    }
}
