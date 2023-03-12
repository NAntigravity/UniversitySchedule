package com.example.universityschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.universityschedule.network.Network
import com.example.universityschedule.network.models.enumconverters.EnumConverter
import com.example.universityschedule.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }

    private lateinit var logoutButton: Button

    private lateinit var name: TextView
    private lateinit var role: TextView
    private lateinit var groupBlock: LinearLayout
    private lateinit var groupNumber: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_frame, container, false)
        logoutButton = view.findViewById(R.id.logoutButton)
        name = view.findViewById(R.id.name)
        role = view.findViewById(R.id.role)
        groupBlock = view.findViewById(R.id.group_number_layout)
        groupNumber = view.findViewById(R.id.groupNumber)

        name.text = Network.getSharedPrefs(MainApplication.UserName)

        when (EnumConverter.getRole(Network.getSharedPrefs(MainApplication.UserRole))) {
            "teacher" -> {
                role.text = resources.getText(R.string.teacher)
                groupBlock.visibility = View.INVISIBLE
            }
            "student" -> {
                role.text = resources.getText(R.string.student)
                groupNumber.text = Network.getSharedPrefs(MainApplication.UserId)
            }
        }

        logoutButton.setOnClickListener {
            viewModel.logout()
            Network.clearUserData()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFrameToAuthorizationFrame())
        }

        return view
    }
}