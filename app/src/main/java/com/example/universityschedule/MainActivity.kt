package com.example.universityschedule

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.CoroutinesErrorHandler
import com.example.universityschedule.network.Network
import com.example.universityschedule.network.models.LoginResponse
import com.example.universityschedule.network.models.basicmodels.LoginRequestBody
import com.example.universityschedule.network.repository.AuthRepository
import com.example.universityschedule.network.testfiles.TestRepository
import com.example.universityschedule.network.testfiles.UserInfo
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding

    private val bottomSheetView by lazy { findViewById<ConstraintLayout>(R.id.bottomSheet) }
    private val topSheetView by lazy { findViewById<ConstraintLayout>(R.id.top) }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var readyToTopSheet = false


    private val textView by lazy { findViewById<TextView>(R.id.state) }
    private val userInfo by lazy { findViewById<TextView>(R.id.userInfo) }

    private val liveData = MutableLiveData<ApiResponse<LoginResponse>>()
    private val userData = MutableLiveData<ApiResponse<UserInfo>>()

    private val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            textView.text = "!!!!!"
            Log.d("!", message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
            Log.d("DATE", "2023-03-02".toLocalDate().dayOfWeek.value.toString())
            Log.d("DATE", "2023-03-05".toLocalDate().dayOfWeek.value.toString())
            CoroutineScope(Dispatchers.IO).launch {
                BaseViewModel().baseRequest(
                    liveData, coroutinesErrorHandler,
                    AuthRepository().login(
                        LoginRequestBody(
                            email = "test@testt.test",
                            password = "112323"
                        )
                    )


                )
            }
        }

        object : CountDownTimer(2000000, 5000) {
            override fun onTick(millisUntilFinished: Long) {
                BaseViewModel().baseRequest(
                    userData,
                    coroutinesErrorHandler,
                    TestRepository().getUserData()
                )
            }

            override fun onFinish() {
            }
        }.start()
        liveData.observe(this) {
            when (it) {
                is ApiResponse.Failure -> textView.text = it.errorMessage
                ApiResponse.Loading -> textView.text = "Loading"
                is ApiResponse.Success -> {
                    textView.text = "SUCCESS!!!!!!"
                    Network.updateToken(it.data.access_token, MainApplication.AccessToken)
                    //Network.updateToken(it.data.refresh_token, MainApplication.RefreshToken)

                }
            }
        }

        userData.observe(this){
            when (it) {
                is ApiResponse.Failure -> userInfo.text = it.errorMessage
                ApiResponse.Loading -> userInfo.text = "Loading"
                is ApiResponse.Success -> {
                    userInfo.text = Network.getToken(MainApplication.AccessToken)
                }
            }
        }


        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
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
                if (newState == BottomSheetBehavior.STATE_HIDDEN){
                    topSheetView.startAnimation(fallingAnimation)
                    topSheetView.visibility = View.VISIBLE
                    readyToTopSheet = false
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