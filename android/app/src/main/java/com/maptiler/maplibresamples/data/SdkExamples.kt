package com.maptiler.maplibresamples.data

import com.maptiler.maplibresamples.CustomMapFragment
import com.maptiler.maplibresamples.SimpleMapFragment
import java.util.*

object SdkExamples {
    val ITEMS: MutableList<SdkSample> = ArrayList()

    val ITEM_MAP: MutableMap<String, SdkSample> = HashMap()

    init {
        addItem(
            SdkSample(
                "Simple Map",
                "Create simple map",
                SimpleMapFragment::class.qualifiedName!!
            )
        )
        addItem(
            SdkSample(
                "Custom Map",
                "Custom layer",
                CustomMapFragment::class.qualifiedName!!
            )
        )
        addItem(
            SdkSample(
                "Annotations",
                "Add annotations to the map",
                SimpleMapFragment::class.qualifiedName!!
            )
        )
        addItem(
            SdkSample(
                "RasterOverlay",
                "Add raster overlay to the map",
                SimpleMapFragment::class.qualifiedName!!
            )
        )
    }

    private fun addItem(item: SdkSample) {
        ITEMS.add(item)
    }

    data class SdkSample(
        val title: String,
        val description: String,
        val targetFragment: String
    ) {
        override fun toString(): String = title
    }
}