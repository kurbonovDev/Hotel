package com.example.hotel.presentation.registration.regist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.hotel.R
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.remote.RegistApi
import com.example.hotel.databinding.FragmentRegistBinding
import com.example.hotel.domain.model.RegisterState
import com.example.hotel.presentation.registration.regist.viewModel.RegistViewModel
import com.example.hotel.presentation.utils.ErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    @Inject
    lateinit var api: RegistApi


    private val viewModel: RegistViewModel by activityViewModels()
    private var _binding: FragmentRegistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.haveAcc.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        createAcc()
        registerUIState()
    }

    private fun createAcc() {
        binding.btnRegister.setOnClickListener {
            val username = binding.userName.text.trim().toString()
            val email = binding.userEmail.text.trim().toString()
            val phone = binding.userPhone.text.trim().toString()
            val password = binding.userPassword.text.trim().toString()

            if (username.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                val userRequest =
                    RegistRequest(
                        name = username,
                        email = email,
                        password = password,
                        phone = phone
                    )

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.preRegistUser(userRequest)
                }

            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun registerUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.preRegisterState.collect { state ->
                    binding.apply {
                        progressbar.isVisible = state is RegisterState.Loading
                        btnRegister.isEnabled = state !is RegisterState.Loading

                    }

                    when (state) {
                        is RegisterState.Loading -> {
                            Unit
                        }

                        is RegisterState.Success -> {
                            val action =
                                RegisterFragmentDirections.actionRegisterFragmentToOtpFragment(
                                    binding.userEmail.text.toString()
                                )
                            findNavController().navigate(action)
                        }

                        is RegisterState.Error -> {
                            ErrorDialog.showDialog(requireContext(), state.errorMessage)
                        }

                        is RegisterState.Empty -> Unit
                    }

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}