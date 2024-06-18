package com.example.hotel.presentation.registration.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.hotel.R
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.databinding.FragmentLoginBinding
import com.example.hotel.domain.model.LoginState
import com.example.hotel.presentation.registration.login.viewModel.LoginViewModel
import com.example.hotel.presentation.registration.regist.viewModel.RegistViewModel
import com.example.hotel.presentation.utils.ErrorDialog
import com.example.hotel.presentation.utils.IS_REGISTERED
import com.example.hotel.presentation.utils.MY_STORAGE
import com.example.hotel.presentation.utils.TOKEN_KEY
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = requireContext().getSharedPreferences(MY_STORAGE, Context.MODE_PRIVATE)
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dontHaveAcc.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        loginUser()
        loginUIState()

    }


    private fun loginUser() {
        binding.btnLogin.setOnClickListener {
            val email = binding.userEmail.text.toString().trim()
            val password = binding.userPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.login(LoginRequest(email = email, password = password))
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loginUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginUIState.collect {state->
                    binding.apply {
                        progressBar.isVisible = state is LoginState.Loading
                    }
                    when(state){
                        is LoginState.Loading->Unit
                        is LoginState.Success->{
                            sharedPreferences?.edit()?.putBoolean(IS_REGISTERED, true)?.apply()
                            sharedPreferences?.edit()?.putString(TOKEN_KEY,state.loginResponse!!.message)?.apply()
                            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                            findNavController().navigate(action)

                        }
                        is LoginState.Error->{
                            ErrorDialog.showDialog(requireContext(),state.errorMessage)
                        }
                        is LoginState.Empty->{}
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