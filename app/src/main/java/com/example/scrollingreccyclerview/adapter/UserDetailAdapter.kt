package com.example.scrollingreccyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scrollingreccyclerview.databinding.ItemLoadingBinding
import com.example.scrollingreccyclerview.databinding.ItemRowDataBinding
import com.example.scrollingreccyclerview.models.Data

class UserDetailAdapter(
    private val userList:MutableList<Data>,
    var context: Context,
    var callbacks: (Data, Int, View) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_LOADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            val itemRowDataBinding =
                ItemRowDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserViewHolder(itemRowDataBinding)
        } else {
            val itemLoadingBinding =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemLoadView(itemLoadingBinding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myDetail = userList[position]

        if (holder is UserViewHolder){
            holder.binding.userName.text = myDetail.name
            holder.binding.userEmail.text = myDetail.email

            Glide.with(context).load(myDetail.profile_picture).into(holder.binding.profilePic)

            holder.itemView.setOnClickListener {
                callbacks.invoke(userList[position], position,holder.binding.profilePic)
            }
        }else if (holder is ItemLoadView){
            Log.d("data_info", "Loading")

        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (userList[position].id==null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int = userList.size


    inner class UserViewHolder(val binding: ItemRowDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemLoadView(private val itemLoadingBinding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(itemLoadingBinding.root) {

    }

}