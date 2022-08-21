package com.accenture.githubapps.features.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.databinding.ItemListBinding
import com.accenture.githubapps.utils.Tools
import com.bumptech.glide.Glide

class FollowAdapter: RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    private var list = ArrayList<User>()

    fun setList(data: List<User>?) {
        val convertToArrayList = Tools.listToArrayList(data)
        if (convertToArrayList != null) {
            list.addAll(convertToArrayList)
        }
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<User> {
        notifyDataSetChanged()
        return list
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

    inner class FollowViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            binding.apply {
                tvTitle.text = data.login

                Glide.with(root)
                    .load(data.avatar_url)
                    .centerCrop()
                    .into(imgAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val items = ItemListBinding.inflate(inflater, parent, false)
        return FollowViewHolder(items)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}