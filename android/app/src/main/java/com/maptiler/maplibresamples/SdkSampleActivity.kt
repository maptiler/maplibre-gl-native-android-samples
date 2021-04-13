package com.maptiler.maplibresamples

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class SdkSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdk_sample)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragmentClass = intent.getStringExtra(ARG_FRAGMENT_CLASS)
            val fragment = Class.forName(fragmentClass).newInstance() as Fragment
            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, SdkSamplesListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        const val ARG_FRAGMENT_CLASS = "fragment_class"
    }
}
