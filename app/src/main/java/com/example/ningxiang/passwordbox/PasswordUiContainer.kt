package com.example.ningxiang.passwordbox

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_password_ui.*
import kotlinx.android.synthetic.main.app_bar_password_ui.*

class PasswordUiContainer : SecurityActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        BaseFragment.OnFragmentInteractionListener {

    private var curFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_ui)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startFragment(EditItemFragment.newInstance(-1))
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

//        nav_view.setCheckedItem(R.id.nav_items)
        switchFragment(MainFragment.newInstance())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {

            // fragment processed this event. just return.
            if (curFragment != null && curFragment!!.onBackPressd()) {
                return
            }

            super.onBackPressed()
        }
    }

    fun switchFragment(fgmt: BaseFragment) = replaceContentFragment(fgmt)

    fun startFragment(fgmt: BaseFragment) = replaceContentFragment(fgmt, true)

    private fun replaceContentFragment(fgmt: BaseFragment, addToStack: Boolean = false) {

        Toast.makeText(this, "replace with fragment ${fgmt.javaClass.simpleName}", Toast.LENGTH_SHORT).show()

        val fgmtTrans = supportFragmentManager.beginTransaction()
                .replace(R.id.content_container, fgmt)

        if (addToStack) {
            fgmtTrans.addToBackStack(null)
        }

        fgmtTrans.commitAllowingStateLoss()

        curFragment = fgmt
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.password_ui, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_items -> {
                switchFragment(MainFragment.newInstance())
            }
//            R.id.nav_settings -> {
//                switchFragment(EditItemFragment.newInstance(3))
//            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
