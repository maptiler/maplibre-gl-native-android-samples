package com.maptiler.maplibresamples

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngQuad
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.RasterLayer
import com.mapbox.mapboxsdk.style.sources.ImageSource
import java.io.InputStream


class RasterOverlayFragment : Fragment(), OnMapReadyCallback {

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(context!!, null)
        activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
            "Raster Overlay"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.raster_overlay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create map view
        mapView = view.findViewById(R.id.rasterMapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {

        val mapTilerKey = Helper.getMapTilerKey(context!!)
        Helper.validateKey(mapTilerKey)
        val styleUrl = "https://api.maptiler.com/maps/streets/style.json?key=${mapTilerKey}"

        // Navigate the map to the area with raster data
        mapboxMap.cameraPosition = CameraPosition.Builder()
            .target(LatLng(50.90013625, 4.64086758))
            .zoom(17.0)
            .build()

        mapboxMap.setStyle(styleUrl) {
            addRasterLayer(it)
        }
    }

    private fun addRasterLayer(style: Style) {
        // Set the latitude and longitude values for the image's four corners
        val quad = LatLngQuad(
            LatLng(50.900867668253724, 4.639663696289062),
            LatLng(50.900867668253724, 4.642066955566406),
            LatLng(50.89935199434383, 4.642066955566406),
            LatLng(50.89935199434383, 4.639663696289062)
        )

        val inputStream: InputStream = context!!.assets.open("aerial_wgs84.png")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        // Add an ImageSource to the map
        style.addSource(ImageSource("raster-image-source", quad, bitmap))

        // Create a raster layer and use the imageSource's ID as the layer's data. Then add a RasterLayer to the map.
        style.addLayer(RasterLayer("raster-image-layer", "raster-image-source"))
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
