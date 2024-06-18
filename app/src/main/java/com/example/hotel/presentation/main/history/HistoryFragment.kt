package com.example.hotel.presentation.main.history

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotel.data.models.history.HistoryResponse
import com.example.hotel.databinding.FragmentHistoryBinding
import com.example.hotel.domain.model.GeneralState
import com.example.hotel.presentation.main.history.adapter.HistoryAdapter
import com.example.hotel.presentation.main.history.viewModel.HistoryViewModel
import com.example.hotel.presentation.utils.ErrorDialog
import com.example.hotel.presentation.utils.MY_STORAGE
import com.example.hotel.presentation.utils.TOKEN_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class HistoryFragment : Fragment() {


    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var rcViewHistory:RecyclerView
    private val historyViewModel: HistoryViewModel by viewModels()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = requireContext().getSharedPreferences(MY_STORAGE, Context.MODE_PRIVATE)
       _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        historyState()


    }
    private fun historyState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                historyViewModel.getApartmentsHistoryState.collect{state->
                    binding.histotyProgressBar.isVisible = state is GeneralState.Loading
                    when(state){
                        is GeneralState.Loading->{

                        }
                        is GeneralState.Success<*> ->{
                            initRcView(state.data as HistoryResponse)
                        }
                        is GeneralState.Error->{
                            ErrorDialog.showDialog(requireContext(), state.errorMessage)
                        }
                        is GeneralState.Empty->{

                        }
                    }

                }
            }
        }
    }

    private fun initRcView(historyResponse: HistoryResponse) {
        rcViewHistory = binding.rcViewHistory
        rcViewHistory.layoutManager = LinearLayoutManager(requireContext())
        rcViewHistory.adapter = HistoryAdapter(historyResponse.data)
    }

    private fun getData(){
        val token = sharedPreferences?.getString(TOKEN_KEY, "")
        if (token!=null){
            historyViewModel.getApartmentHistory("Bearer $token")
        }else{
            Toast.makeText(requireContext(), "Ошибка с токеном", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}