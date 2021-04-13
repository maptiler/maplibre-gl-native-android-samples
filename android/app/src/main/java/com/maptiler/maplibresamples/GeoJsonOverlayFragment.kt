package com.maptiler.maplibresamples

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mapbox.geojson.FeatureCollection
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.*


class GeoJsonOverlayFragment : Fragment() {

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(context!!, null)
        activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
            "GeoJson Overlay"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.geojson_overlay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapTilerKey = Helper.getMapTilerKey(context!!)
        Helper.validateKey(mapTilerKey)
        val styleUrl = "https://api.maptiler.com/maps/streets/style.json?key=${mapTilerKey}";

        // Create map view
        mapView = view.findViewById(R.id.geoJsonMapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->

            // Navigate the map to the area with geojson data
            map.cameraPosition = CameraPosition.Builder()
                .target(LatLng(45.5076, -122.6736))
                .zoom(11.0)
                .build()

            // Load the style and load geoJson after the style has been loaded
            map.setStyle(styleUrl) {
                GeoJsonLoader(this, it).execute()
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
}

private class GeoJsonLoader(fragment: GeoJsonOverlayFragment, val style: Style) :
    AsyncTask<Void, Void, FeatureCollection?>() {
    private val weakReference: WeakReference<GeoJsonOverlayFragment> = WeakReference(fragment)

    override fun doInBackground(vararg params: Void): FeatureCollection? {
        val fragment: GeoJsonOverlayFragment? = weakReference.get()
        if (fragment != null) {
            val context = fragment.activity?.applicationContext
            if (context != null) {
                val inputStream: InputStream = context.assets.open("example.geojson")
                return FeatureCollection.fromJson(convertStreamToString(inputStream))
            }
        }

        return null
    }

    override fun onPostExecute(featureCollection: FeatureCollection?) {
        super.onPostExecute(featureCollection)
        val fragment: GeoJsonOverlayFragment? = weakReference.get()
        if (fragment != null && featureCollection != null) {
            drawLines(featureCollection)
        }
    }

    private fun drawLines(featureCollection: FeatureCollection) {
        if (featureCollection.features() != null) {
            if (featureCollection.features()!!.size > 0) {
                style.addSource(GeoJsonSource("line-source", featureCollection))

                // The layer properties for our line. This is where we make the line dotted, set the
                // color, etc.
                style.addLayer(
                    LineLayer("linelayer", "line-source")
                        .withProperties(
                            PropertyFactory.lineCap(Property.LINE_CAP_SQUARE),
                            PropertyFactory.lineJoin(Property.LINE_JOIN_MITER),
                            PropertyFactory.lineOpacity(.7f),
                            PropertyFactory.lineWidth(7f),
                            PropertyFactory.lineColor(Color.parseColor("#3bb2d0"))
                        )
                )
            }
        }
    }

    companion object {
        fun convertStreamToString(`is`: InputStream?): String {
            val scanner: Scanner = Scanner(`is`).useDelimiter("\\A")
            return if (scanner.hasNext()) scanner.next() else ""
        }
    }
}
