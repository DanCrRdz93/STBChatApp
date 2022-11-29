package com.daniel.stbchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.daniel.stbchatapp.ui.views.MessageFragment
import com.daniel.stbchatapp.ui.views.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
       menuInflater.inflate(R.menu.settings_menu, menu)
        (menu?.findItem(R.id.settings_menu) as MenuItem).setOnMenuItemClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SettingsFragment())
                .commit()
            true
        }
        return true
    }
}