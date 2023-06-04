package com.example.fit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolb: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolb)

        drawerLayout = findViewById(R.id.drawer_layout)
        var toogle: ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolb,R.string.navigation_open,R.string.navigation_close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        { drawerLayout.closeDrawer(GravityCompat.START) }
        else{
            super.onBackPressed()
        }
    }
}