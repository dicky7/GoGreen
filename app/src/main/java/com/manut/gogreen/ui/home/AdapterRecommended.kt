package com.manut.gogreen.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manut.gogreen.data.source.remote.response.ResponseRecommendation
import com.manut.gogreen.databinding.ItemRecommendBinding

class AdapterRecommended : RecyclerView.Adapter<AdapterRecommended.MyAdapter>() {
    var onItemClick: ((ResponseRecommendation) -> Unit)? = null
    private val  item = ArrayList<ResponseRecommendation>()
    fun setData(items: ArrayList<ResponseRecommendation>) {
        item.clear()
        item.addAll(items)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter =
            MyAdapter(
                    ItemRecommendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            )

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
       holder.bind(item[position])
    }


    inner class MyAdapter(private val binding : ItemRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : ResponseRecommendation){
        with(binding){
            Glide.with(itemView.context).load(item.icon).into(imgPoster)
            tvItemTitle.text = item.name


        }

    }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(item[adapterPosition])
            }
        }
    }
}