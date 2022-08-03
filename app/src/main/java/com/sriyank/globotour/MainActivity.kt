package com.sriyank.globotour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.list_item_city.*
import android.util.Log
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar        : MaterialToolbar
    private lateinit var navController  : NavController
    private lateinit var navigationView : NavigationView
    private lateinit var drawerLayout   : DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        toolbar         = findViewById(R.id.activity_main_toolbar)
        navigationView = findViewById(R.id.nav_view)
        drawerLayout   = findViewById(R.id.drawer_layout)

        // Get NavHostFragment and NavController
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController   = navHostFrag.navController

        // Define AppBarConfiguration: Connect DrawerLayout with Navigation Component
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout) // reference to instant of nav_graph.xml
                                                      //navController.graph part of Navigation Component
        // Connect Toolbar with NavController
        toolbar.setupWithNavController(navController, appBarConfiguration)
                                    // i got navController from navController = navHostFrag.navController
                                    // and appBarConfiguration got it from  val appBarConfiguration = AppBarConfiguration

        // Connect NavigationView With NavController
        navigationView.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        if (drawerLayout.isOpen)
            drawerLayout.close()
        else
            super.onBackPressed()
    }

}