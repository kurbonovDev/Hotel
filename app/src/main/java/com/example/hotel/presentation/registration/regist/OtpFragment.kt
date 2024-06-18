package com.example.hotel.presentation.registration.regist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.databinding.FragmentOtpBinding
import com.example.hotel.domain.model.RegisterState
import com.example.hotel.presentation.registration.regist.viewModel.RegistViewModel
import com.example.hotel.presentation.utils.ErrorDialog
import com.example.hotel.presentation.utils.IS_REGISTERED
import com.example.hotel.presentation.utils.MY_STORAGE
import com.example.hotel.presentation.utils.TOKEN_KEY
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OtpFragment : Fragment() {


    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!
    private val args: OtpFragmentArgs by navArgs()
    private var login: String? = null
    private val viewModel: RegistViewModel by activityViewModels()
    private var sharedPreferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = requireContext().getSharedPreferences(MY_STORAGE, Context.MODE_PRIVATE)
        _binding = FragmentOtpBinding.inflate(layoutInflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login = args.login
        timer()
        sendOtp()
        registerUIState()
    }

    private fun sendOtp() {
        binding.otpCode.doAfterTextChanged {
            if (it.toString().length == 4) {
                viewLifecycleOwner.lifecycleScope.launch {
                 viewModel.registUser(RegistFinish(email = login!!, code = it.toString()))
                }

            }
        }
    }

    private fun registerUIState() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collectLatest { state ->

                    binding.apply {
                        progressbar.isVisible = state is RegisterState.Loading
                    }
                    when (state) {
                        is RegisterState.Loading -> {
                            Unit
                        }

                        is RegisterState.Success -> {

                            sharedPreferences?.edit()?.putBoolean(IS_REGISTERED, true)?.apply()
                            sharedPreferences?.edit()?.putString(TOKEN_KEY, state.userResponse!!.message)?.apply()

                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                            val action = OtpFragmentDirections.actionOtpFragmentToMainFragment()
                            findNavController().navigate(action)
                        }
                        is RegisterState.Error -> {
                            ErrorDialog.showDialog(requireContext(),state.errorMessage)
                        }

                        is RegisterState.Empty -> {
                            Unit
                        }
                    }
                }
            }
        }
    }

    private fun timer() {
        val timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000
                binding.resendOtpBtn.text = "Resend in ${String.format("%02d", second)}"
                binding.resendOtpBtn.isEnabled = false
            }

            override fun onFinish() {
                binding.resendOtpBtn.text = "Resend"
                binding.resendOtpBtn.isEnabled = true
            }
        }
        timer.start()
    }


}