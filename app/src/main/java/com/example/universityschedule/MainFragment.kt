package com.example.universityschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.universityschedule.network.Network

class MainFragment : Fragment() {


    private lateinit var toMenuButton: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_frame, container, false)

        val roomId = requireArguments().getString("roomId")
        val groupId = requireArguments().getString("groupId")
        val teacherId = requireArguments().getString("teacherId")

        toMenuButton = view.findViewById(R.id.toMenuButton)

        toMenuButton.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFrameToMenuFrame(
                    Network.userAuthorized
                )
            )
        }

        return view
    }
}