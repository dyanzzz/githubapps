package com.accenture.githubapps.features.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.databinding.ItemListBinding
import com.accenture.githubapps.utils.Tools
import com.bumptech.glide.Glide

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private var list = ArrayList<User>()

    fun setList(data: List<User>?) {
        val convertToArrayList = Tools.listToArrayList(data)
        if (convertToArrayList != null) {
            list.addAll(convertToArrayList)
        }
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

    private lateinit var homeCallback: HomeCallback
    fun setHomeCallback(homeCallback: HomeCallback) {
        this.homeCallback = homeCallback
    }

    inner class HomeViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {

            binding.root.setOnClickListener {
                homeCallback.onClick(data)
            }

            binding.apply {
                tvTitle.text = data.login

                Glide.with(root)
                    .load(data.avatar_url)
                    .centerCrop()
                    .into(imgAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val items = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(items)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}