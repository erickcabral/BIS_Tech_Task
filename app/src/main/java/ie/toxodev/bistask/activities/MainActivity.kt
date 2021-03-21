package ie.toxodev.bistask.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ie.toxodev.bistask.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostMain)
        this.appBarConfiguration = AppBarConfiguration(setOf(R.id.viewDisplay), null)

        setSupportActionBar(findViewById(R.id.toolbar))

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return this.navController.navigateUp(this.appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}