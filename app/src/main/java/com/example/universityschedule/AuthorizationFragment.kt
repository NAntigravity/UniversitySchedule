package com.example.universityschedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.Network
import com.example.universityschedule.network.models.enumconverters.EnumConverter
import com.example.universityschedule.viewmodels.AuthorizationViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputLayout

class AuthorizationFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[AuthorizationViewModel::class.java] }

    private lateinit var bottomSheetView: ConstraintLayout
    private lateinit var topSheetView: ConstraintLayout
    private lateinit var toScheduleButton: Button

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var readyToTopSheet = false

    private lateinit var loginEmail: TextInputLayout
    private lateinit var loginPassword: TextInputLayout
    private lateinit var registerEmail: TextInputLayout
    private lateinit var registerPassword: TextInputLayout

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.authorization_frame, container, false)
        initAllVars(view)

        loginButton.setOnClickListener {
            viewModel.login(
                email = loginEmail.editText?.text.toString(),
                password = loginPassword.editText?.text.toString()
            )
        }
        registerButton.setOnClickListener {
            viewModel.register(
                email = registerEmail.editText?.text.toString(),
                password = registerPassword.editText?.text.toString()
            )
        }

        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiResponse.Loading -> Log.d("nav", "Trying to login/register")
                is ApiResponse.Failure -> {
                    Log.d("nav", "fail to login/register")
                }
                is ApiResponse.Success -> {
                    Log.d("nav", "SUCCESS")
                    Network.updateSharedPrefs(MainApplication.AccessToken, it.data.accessToken)
                    Network.updateSharedPrefs(MainApplication.RefreshToken, it.data.refreshToken)

                    Network.userAuthorized = true

                    val userId = Network.getSharedPrefs(MainApplication.UserId)
                    when (EnumConverter.getRole(Network.getSharedPrefs(MainApplication.UserRole))) {
                        "teacher" -> findNavController().navigate(
                            AuthorizationFragmentDirections.actionAuthorizationFrameToMainFrame(
                                teacherId = userId
                            )
                        )
                        "student" -> findNavController().navigate(
                            AuthorizationFragmentDirections.actionAuthorizationFrameToMainFrame(
                                groupId = userId
                            )
                        )
                    }


                }
            }
        }


        toScheduleButton.setOnClickListener {
            findNavController().navigate(
                AuthorizationFragmentDirections.actionAuthorizationFrameToMenuFrame(false)
            )
        }


        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        setBottomSheetVisibility(false)

        val fallingAnimation: Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_from_top)
        val riseAnimation: Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_back_to_top)

        val button: Button = view.findViewById(R.id.openRegisterForm)
        button.setOnClickListener {
            setBottomSheetVisibility(true)
            riceTopSheet(riseAnimation)
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED && readyToTopSheet) {
                    topSheetView.startAnimation(fallingAnimation)
                    topSheetView.visibility = View.VISIBLE
                    toScheduleButton.visibility = View.VISIBLE
                    readyToTopSheet = false
                }
                if (newState == BottomSheetBehavior.STATE_EXPANDED && !readyToTopSheet) {
                    if (topSheetView.visibility == View.VISIBLE) { // android bug
                        riceTopSheet(riseAnimation)
                    }
                    readyToTopSheet = true
                }
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    topSheetView.startAnimation(fallingAnimation)
                    topSheetView.visibility = View.VISIBLE
                    toScheduleButton.visibility = View.VISIBLE
                    readyToTopSheet = false
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        return view
    }

    private fun initAllVars(view: View) {
        bottomSheetView = view.findViewById(R.id.bottomSheet)
        topSheetView = view.findViewById(R.id.top)
        toScheduleButton = view.findViewById(R.id.to_schedule)

        loginEmail = view.findViewById(R.id.loginEmailField)
        loginPassword = view.findViewById(R.id.loginPasswordField)
        registerEmail = view.findViewById(R.id.registerEmailField)
        registerPassword = view.findViewById(R.id.registerPasswordField)

        loginButton = view.findViewById(R.id.login_button)
        registerButton = view.findViewById(R.id.register_button)
    }

    private fun riceTopSheet(riseAnimation: Animation) {
        MainActivity().clearKeyboardAndCurrentFocus()
        topSheetView.startAnimation(riseAnimation)
        topSheetView.visibility = View.INVISIBLE
        toScheduleButton.visibility = View.INVISIBLE
        readyToTopSheet = true
    }

    private fun setBottomSheetVisibility(isVisible: Boolean) {
        val updatedState =
            if (isVisible) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.state = updatedState
    }


}