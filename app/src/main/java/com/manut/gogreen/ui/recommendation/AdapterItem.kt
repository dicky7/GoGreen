package com.manut.gogreen.ui.recommendation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manut.gogreen.data.source.remote.response.RecommendationItem
import com.manut.gogreen.databinding.ItemRecommendResultBinding

class AdapterItem : RecyclerView.Adapter<AdapterItem.MyAdapterItem>() {

    private val  item = ArrayList<RecommendationItem>()
    fun setData(items: ArrayList<RecommendationItem>) {
        item.clear()
        item.addAll(items)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterItem =
        MyAdapterItem(
                ItemRecommendResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            )

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: MyAdapterItem, position: Int) {
       holder.bind(item[position])
    }


    inner class MyAdapterItem(private val binding : ItemRecommendResultBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : RecommendationItem){
        with(binding){
            Glide.with(itemView.context).load(item.image).into(imgPosterItem)
            tvItemTitle.text = item.name
            tvItemDesc.text = item.desc

        }
    }
    }
}