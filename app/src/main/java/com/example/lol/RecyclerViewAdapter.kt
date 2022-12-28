package com.example.lol

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lol.databinding.ItemViewBinding
import com.example.lol.patter.MainActivity
import com.example.lol.retrofit.LOLResponse.LOLResponseItem

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private val list = mutableListOf<LOLResponseItem>()

    fun updateList(items: MutableList<LOLResponseItem>) {
        val diffCallback = DiffUtilCallback(list, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(items)

        diffResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LOLResponseItem) {
            //이부분 오류
            binding.data = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemViewBinding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}