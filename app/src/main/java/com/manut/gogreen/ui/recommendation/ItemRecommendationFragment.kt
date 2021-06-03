package com.manut.gogreen.ui.recommendation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.manut.gogreen.R
import com.manut.gogreen.data.source.remote.response.RecommendationItem
import com.manut.gogreen.data.source.remote.response.ResponseRecommendation
import com.manut.gogreen.databinding.FragmentHomeBinding
import com.manut.gogreen.databinding.FragmentItemRecommendationBinding
import com.manut.gogreen.databinding.ItemRecommendBinding
import com.manut.gogreen.ui.home.HomeViewModel


class ItemRecommendationFragment : Fragment() {
    private var _binding: FragmentItemRecommendationBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: ItemViewModel by viewModels()
    private var data: ResponseRecommendation? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentItemRecommendationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            val itemAdapter = AdapterItem()
             data = ItemRecommendationFragmentArgs.fromBundle(arguments as Bundle).itemRecomm
            homeViewModel.isLoading.observe(viewLifecycleOwner, {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
            homeViewModel.toastText.observe(viewLifecycleOwner,{
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
            Glide.with(this).load(data?.icon).into(binding.imgDetail)
            binding.tvDetailName.text = data?.name
            val category = data?.name
            homeViewModel.getRecommend().observe(viewLifecycleOwner,{ item ->
                itemAdapter.setData(item)
                itemAdapter.notifyDataSetChanged()
            })
            with(binding.rvItemDetail){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = itemAdapter
            }
            if (category != null) {
                homeViewModel.setRecommendation(category)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}