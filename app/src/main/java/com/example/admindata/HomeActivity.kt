package com.example.admindata

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.admindata.Fragment.DriverDataFragment
import com.example.admindata.Fragment.HomeFragment
import com.example.admindata.Fragment.UserDataFragment
import com.google.android.material.navigation.NavigationView


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var bottomNavigation: MeowBottomNavigation
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var imgMenu: ImageView


    private val Home = 1
    private val Driver = 2
    private val User = 3
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNavigation = findViewById(R.id.BottomNavigation)
        bottomNavigation.add(MeowBottomNavigation.Model(Home, R.drawable.home))
        bottomNavigation.add(MeowBottomNavigation.Model(Driver, R.drawable.driver))
        bottomNavigation.add(MeowBottomNavigation.Model(User, R.drawable.user))
        titleTextView = findViewById(R.id.TxTitle)
        drawerLayout = findViewById(R.id.DrawerLayout)
        imgMenu = findViewById(R.id.ImgMenu)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        imgMenu.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            navigationView.setCheckedItem(R.id.item_Home)

        }

        bottomNavigation.show(Home, true)

        bottomNavigation.setOnClickMenuListener { model ->
            // Handle click events for bottom navigation items
            when (model.id) {
                Home -> Toast.makeText(this@HomeActivity, "Home clicked", Toast.LENGTH_SHORT).show()
                Driver -> Toast.makeText(this@HomeActivity, "Driver clicked", Toast.LENGTH_SHORT)
                    .show()

                User -> Toast.makeText(this@HomeActivity, "User clicked", Toast.LENGTH_SHORT).show()
            }
            null
        }
        supportActionBar?.title = title
        bottomNavigation.setOnShowListener { model ->
            // Update app bar title based on the selected fragment
            titleTextView.text = when (model.id) {
                Home -> "Home Page"
                Driver -> "Driver Page"
                User -> "User Page"
                else -> "Unknown Page"
            }
            val fragment: Fragment = when (model.id) {
                Home -> HomeFragment()
                Driver -> DriverDataFragment()
                User -> UserDataFragment()
                else -> Fragment()
            }


            // Load fragment corresponding to the selected bottom navigation item

            loadAndReloadFragment(fragment)

            null
        }

    }


    private fun loadAndReloadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.FragmentContainer, fragment).commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.FragmentContainer, fragment)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            androidx.appcompat.R.id.home -> replaceFragment(HomeFragment())
            R.id.item_Share -> shareApp(this)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    private fun shareApp(context: Context) {
        val appPackageName = context.packageName
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Check out this amazing app: https://play.google.com/store/apps/details?id=$appPackageName"
        )
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }



    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}


