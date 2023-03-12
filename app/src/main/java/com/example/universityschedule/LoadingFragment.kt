package com.example.universityschedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.Network
import com.example.universityschedule.network.models.UserInfo
import com.example.universityschedule.network.models.enumconverters.EnumConverter
import com.example.universityschedule.viewmodels.ApplicationLoadingViewModel

class LoadingFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[ApplicationLoadingViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.loading_frame, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val liveData: MutableLiveData<ApiResponse<UserInfo>> =
            MutableLiveData<ApiResponse<UserInfo>>()

        viewModel.checkUserAlive(liveData)

        liveData.observe(viewLifecycleOwner) {
            when (it) {
                ApiResponse.Loading -> Log.d("nav", "LOADING")
                is ApiResponse.Failure -> {
                    Log.d("nav", "FAIL")
                    findNavController().navigate(LoadingFragmentDirections.actionLoadingFrameToAuthorization())
                }
                is ApiResponse.Success -> {
                    Network.userAuthorized = true
                    //findNavController().navigate(LoadingFragmentDirections.actionLoadingFrameToAuthorization())
                    when(EnumConverter.getRole(it.data.role.toString())){
                        "teacher" -> findNavController().navigate(
                            LoadingFragmentDirections.actionLoadingFrameToMainFrame(
                                teacherId = it.data.defaultId
                            )
                        )
                        "student" -> findNavController().navigate(
                            LoadingFragmentDirections.actionLoadingFrameToMainFrame(
                                groupId = it.data.defaultId
                            )
                        )
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}