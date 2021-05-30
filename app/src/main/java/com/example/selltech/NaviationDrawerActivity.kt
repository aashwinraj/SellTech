package com.example.selltech
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.selltech.databinding.ActivityNaviationDrawerBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class NaviationDrawerActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityNaviationDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityNaviationDrawerBinding.inflate(layoutInflater)
     setContentView(binding.root)


       setSupportActionBar(binding.appBarNaviationDrawer.toolbar)

//        binding.appBarNaviationDrawer.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_naviation_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,R.id.nav_profile, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_contactUs,R.id.navOrders), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.naviation_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        when (id) {
            R.id.action_logOut -> {
                mAuth.signOut()
                finish()
                startActivity(Intent(this,MainActivity::class.java))
            }
            R.id.action_settings->{
                startActivity(Intent(this,SettinsActivity::class.java))
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_naviation_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}