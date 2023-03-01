package com.example.universityschedule

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.universityschedule.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding

    private val bottomSheetView by lazy { findViewById<ConstraintLayout>(R.id.bottomSheet) }
    private val topSheetView by lazy { findViewById<ConstraintLayout>(R.id.top) }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var readyToTopSheet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_main)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        setBottomSheetVisibility(false)


        val fallingAnimation: Animation =
            AnimationUtils.loadAnimation(this, R.anim.slide_out_from_top)
        val riseAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_back_to_top)

        val button: Button = findViewById(R.id.openRegisterForm)
        button.setOnClickListener {
            setBottomSheetVisibility(true)
        }


        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED && readyToTopSheet) {
                    topSheetView.startAnimation(fallingAnimation)
                    topSheetView.visibility = View.VISIBLE
                    readyToTopSheet = false
                }
                if (newState == BottomSheetBehavior.STATE_EXPANDED && !readyToTopSheet) {
                    clearKeyboardAndCurrentFocus()
                    topSheetView.startAnimation(riseAnimation)
                    topSheetView.visibility = View.INVISIBLE
                    readyToTopSheet = true
                }
                Log.d("!", newState.toString())
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })


//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//
//        setSupportActionBar(binding.toolbar)
//
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAnchorView(R.id.fab)
//                .setAction("Action", null).show()
//        }
    }

    private fun setBottomSheetVisibility(isVisible: Boolean) {
        val updatedState =
            if (isVisible) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.state = updatedState
    }

    fun clearKeyboardAndCurrentFocus() {
        currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}