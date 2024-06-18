package com.example.hotel.presentation.main.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.hotel.R
import com.example.hotel.data.models.user.User
import com.example.hotel.data.models.user.UserResponse
import com.example.hotel.databinding.FragmentProfileBinding
import com.example.hotel.domain.model.GeneralState
import com.example.hotel.presentation.main.profile.viewModel.UserViewModel
import com.example.hotel.presentation.utils.MY_STORAGE
import com.example.hotel.presentation.utils.TOKEN_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = requireContext().getSharedPreferences(MY_STORAGE, Context.MODE_PRIVATE)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        userState()
        binding.exit.setOnClickListener {
            sharedPreferences?.edit()?.clear()?.apply()
            val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun getData(){
        val token = sharedPreferences?.getString(TOKEN_KEY, "")
        userViewModel.getUser("Bearer $token")
    }
    private fun userState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                userViewModel.userUIState.collect{state->
                    binding.progressBar4.isVisible = state is GeneralState.Loading
                    when(state){
                        is GeneralState.Success<*> ->{
                            initUser((state.data as UserResponse).data)
                        }
                        is GeneralState.Error->{

                        }
                        is GeneralState.Loading->{

                        }
                        is GeneralState.Empty->{

                        }
                    }

                }
            }
        }
    }

    private fun initUser(user: User){
        binding.name.text = user.name
        binding.email.text = user.email
        binding.phone.text ="+992 ${user.phone}"
    }


}