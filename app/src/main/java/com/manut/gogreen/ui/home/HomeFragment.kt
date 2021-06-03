package com.manut.gogreen.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.manut.gogreen.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      _binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            val adapterRecommended = AdapterRecommended()
            adapterRecommended.onItemClick = {selectData ->
             val toDetail = HomeFragmentDirections.actionNavigationHomeToItemRecommendationFragment(selectData)
                view.findNavController().navigate(toDetail)
            }
            homeViewModel.isLoading.observe(viewLifecycleOwner, {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
            homeViewModel.toastText.observe(viewLifecycleOwner,{
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })

            homeViewModel.getRecommend().observe(viewLifecycleOwner,{list->
                adapterRecommended.setData(list)
                adapterRecommended.notifyDataSetChanged()
            })
            with(binding.rvRecommend){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = adapterRecommended
            }
            homeViewModel.setRecommendation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}