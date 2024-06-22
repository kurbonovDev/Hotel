package com.example.hotel.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.hotel.R
import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.databinding.FragmentHomeBinding
import com.example.hotel.presentation.main.home.adapter.TopRoomsAdapter
import com.example.hotel.presentation.main.home.adapter.ViewPagerAdapter
import com.example.hotel.presentation.main.home.viewModel.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var rcViewRooms: RecyclerView
    private lateinit var  adapter:TopRoomsAdapter

    private val homeViewModel: HomeViewModel by activityViewModels()

    @DrawableRes
    private var imageList = mutableListOf(
        R.drawable.hotel1, R.drawable.hotel2, R.drawable.hotel3
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        //getData()
       // apartmentsUIState()
    }

     private fun initViewPager() {
          viewPager2 = binding.viewPager
          viewPagerAdapter = ViewPagerAdapter(imageList)
          viewPager2.adapter = viewPagerAdapter
          viewPager2.offscreenPageLimit = 1
          viewPager2.clipToPadding = false
          viewPager2.clipChildren = false
          viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
          binding.dotsIndicator.attachTo(viewPager2)
      }


    /*private fun getData() {
        homeViewModel.getApartments()
    }*/

   /* private fun apartmentsUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.apartmentsState.collectLatest { state ->
                    binding.apply {
                        progressBar2.isVisible = state is ApartmentsState.Loading
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

                        is ApartmentsState.Empty -> {
                           Unit
                        }
                    }
                }
            }
        }
    }*/

    private fun UI(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.apartmentsState.collect{
                    
                }
            }
        }

    }
    private fun initRcView(apartments: RoomsDTO?) {
        rcViewRooms = binding.rcViewRooms
        if (apartments != null)
            rcViewRooms.adapter = TopRoomsAdapter(apartments)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}