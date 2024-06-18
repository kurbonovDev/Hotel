package com.example.hotel.presentation.main.rooms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotel.R
import com.example.hotel.databinding.FragmentRoomsBinding
import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.domain.model.ApartmentsState
import com.example.hotel.presentation.main.home.viewModel.HomeViewModel
import com.example.hotel.presentation.main.rooms.adapter.RoomsAdapter
import com.example.hotel.presentation.utils.ErrorDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class RoomsFragment : Fragment() {


    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RoomsAdapter
    private lateinit var rcViewRoom:RecyclerView

    private val homeViewModel:HomeViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomsBinding.inflate(layoutInflater, container, false)
        return _binding!!.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomsUIState()
    }

    private fun roomsUIState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.apartmentsState.collectLatest { state ->
                    binding.apply {
                        progresbar.isVisible = state is ApartmentsState.Loading
                        rcViewRooms.isVisible = state is ApartmentsState.Success
                        binding.emptyTextView.isVisible = state is ApartmentsState.Empty
                    }
                    when (state) {
                        is ApartmentsState.Loading -> Unit
                        is ApartmentsState.Success -> {
                            initRcView(state.apartments)
                        }

                        is ApartmentsState.Error -> {
                            ErrorDialog.showDialog(requireContext(), state.errorMessage)
                        }

                        is ApartmentsState.Empty -> Unit
                    }
                }
            }
        }
    }
    private fun initRcView(roomsList:RoomsDTO?){
        if (roomsList!=null){
            rcViewRoom = binding.rcViewRooms
            rcViewRoom.layoutManager = LinearLayoutManager(requireContext())
            adapter = RoomsAdapter(roomsList){
                val action = RoomsFragmentDirections.actionRoomsFragmentToRoomInfoFragment(it)
                findNavController().navigate(action)
            }
            rcViewRoom.adapter = adapter
        }

    }

}