package com.charging.practiceapp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.charging.practiceapp.model.Details
import com.charging.practiceapp.home.HomeViewModel
import androidx.navigation.fragment.findNavController
import com.charging.practiceapp.adapter.DetailsAdapter
import com.charging.practiceapp.database.DetailsRoomDB
import androidx.recyclerview.widget.LinearLayoutManager
import com.charging.practiceapp.home.HomeViewModelFactory
import com.charging.practiceapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    var binding: FragmentHomeBinding? = null
    lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding?.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnAddDetails?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formFragment)
        }
        val adapter = DetailsAdapter()
        var db: DetailsRoomDB = DetailsRoomDB.getDatabase(requireContext())
        val viewModelFactory = HomeViewModelFactory(db)
        homeViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(HomeViewModel::class.java)
        binding?.recyclerview?.apply {
            this.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }
        homeViewModel.listDetails.observe(viewLifecycleOwner, object : Observer<List<Details>> {
            override fun onChanged(t: List<Details>?) {
                adapter.submitList(t);
            }
        })
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getAllDetailsList()
    }
}