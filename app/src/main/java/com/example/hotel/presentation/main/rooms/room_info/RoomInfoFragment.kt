package com.example.hotel.presentation.main.rooms.room_info

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.hotel.data.models.apartaments.BookingRequest
import com.example.hotel.data.models.apartaments.RoomInfoItem
import com.example.hotel.data.models.apartaments.RoomItem
import com.example.hotel.databinding.FragmentRoomInfoBinding
import com.example.hotel.domain.model.GeneralState
import com.example.hotel.presentation.main.home.viewModel.HomeViewModel
import com.example.hotel.presentation.main.rooms.adapter.RoomViewPagerAdapter
import com.example.hotel.presentation.main.rooms.viewModel.RoomInfoViewModel
import com.example.hotel.presentation.utils.ErrorDialog
import com.example.hotel.presentation.utils.MY_STORAGE
import com.example.hotel.presentation.utils.TOKEN_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class RoomInfoFragment : Fragment() {

    private var _binding: FragmentRoomInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager2: ViewPager2
    private val roomInfoViewModel: RoomInfoViewModel by viewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val args: RoomInfoFragmentArgs by navArgs()
    private var roomItem: RoomItem? = null
    private var sharedPreferences: SharedPreferences? = null
    private var fromDate: String? = null
    private var toDate: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = requireContext().getSharedPreferences(MY_STORAGE, Context.MODE_PRIVATE)
        _binding = FragmentRoomInfoBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomItem = args.roomInfo
        roomInfoViewModel.getRoomImages(roomItem!!.id)
        roomInfoState()
        binding.fromDate.setOnClickListener {
            showFromDatePickerDialog()
        }
        binding.toDate.setOnClickListener {
            showToDatePickerDialog()
        }
        initUI()
        bookApartment()
        bookingState()
    }

    private fun initViewPager(roomInfoItem: RoomInfoItem) {
        viewPager2 = binding.viewPager
        viewPager2.adapter = RoomViewPagerAdapter(roomInfoItem)
        viewPager2.offscreenPageLimit = 1
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

    private fun roomInfoState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                roomInfoViewModel.roomInfoState.collect { state ->
                    binding.progressBar3.isVisible = state is GeneralState.Loading
                    when (state) {
                        is GeneralState.Loading -> Unit
                        is GeneralState.Success<*> -> {
                            initViewPager(state.data as RoomInfoItem)
                        }

                        is GeneralState.Error -> {
                            ErrorDialog.showDialog(requireContext(), state.errorMessage)
                        }

                        is GeneralState.Empty -> Unit
                    }
                }
            }
        }
    }

    private fun initUI() {
        binding.roomName.text = roomItem?.name
        binding.roomPrice.text = "За день:${roomItem?.price.toString()}"
        binding.roomDeck.text = roomItem?.description
    }


    private fun showFromDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.fromDate.text = "От $selectedDate"
                fromDate = selectedDate

            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun showToDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.toDate.text = "До $selectedDate"
                toDate = selectedDate

            },
            year,
            month,
            day
        )
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }


    private fun bookApartment() {
        binding.btnBookRoom.setOnClickListener {
            val token = sharedPreferences?.getString(TOKEN_KEY, "")
            Log.d("My_TAG", "bookApartment: $token")
            if (token != null) {
                if (fromDate != null && toDate != null) {
                    roomInfoViewModel.bookApartment(
                        "Bearer $token",
                        BookingRequest(
                            args.roomInfo.id,
                            fromDate ?: "",
                            toDate ?: ""
                        )

                    ){
                        homeViewModel.clearData()
                        homeViewModel.getApartments()

                    }



                } else {
                    ErrorDialog.showDialog(requireContext(), "Выберите дату заезда и выезда")
                }

            } else {
                ErrorDialog.showDialog(
                    requireContext(),
                    "Токен не найден выйдите из приложения и входите снова!!"
                )
            }

        }
    }


    private fun bookingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                roomInfoViewModel.bookApartmentState.collect { state ->
                    binding.btnBookRoom.isEnabled = state !is GeneralState.Loading

                    when(state){
                        is GeneralState.Loading -> Unit
                        is GeneralState.Success<*> -> {
                            val action = RoomInfoFragmentDirections.actionRoomInfoFragmentToRoomsFragment()
                            findNavController().navigate(action)
                        }
                        is GeneralState.Error -> {
                            ErrorDialog.showDialog(requireContext(), state.errorMessage)
                        }
                        is GeneralState.Empty -> Unit
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