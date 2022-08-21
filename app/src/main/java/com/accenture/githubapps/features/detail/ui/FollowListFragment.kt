package com.accenture.githubapps.features.detail.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.accenture.githubapps.databinding.FragmentFollowBinding
import com.accenture.githubapps.di.Injectable
import com.accenture.githubapps.di.injectViewModel
import com.accenture.githubapps.extension.visible
import com.accenture.githubapps.utils.HelperLoading
import com.bumptech.glide.Glide
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class FollowListFragment(
    private val statusFollow: String,
    private val userName: String,
): Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var detailViewModel: DetailViewModel

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FollowAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        context ?: return binding.root

        AndroidInjection.inject(requireActivity())
        detailViewModel = injectViewModel(viewModelFactory)

        adapter = FollowAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            layoutManager = LinearLayoutManager(activity)
            rv.setHasFixedSize(true)
            rv.layoutManager = layoutManager
            rv.adapter = adapter
        }

        initComponent()

        return binding.root
    }

    private fun initComponent() {
        detailViewModel.setUserFollow(statusFollow, userName)
        getAllFollower(statusFollow)
    }

    private fun getAllFollower(statusFollow: String) {
        adapter.clear()
        detailViewModel.getUserFollow().observe(viewLifecycleOwner) { result ->
            binding.apply {
                HelperLoading.hideLoading()
                adapter.setList(result)
            }
        }
    }

    /*private fun selectCustomer() {
        adapter.customersCallback(object : CustomerCallback {
            override fun onItemClick(data: MstCustomers) {
                HawkUtils().setLocalStorage(
                    selectedCustomer = data
                )
                Timber.e(data.toString())
                val direction =
                    VisitFragmentDirections.actionVisitFragmentToVisitMapFragment(data.ID!!)
                requireView().findNavController().navigate(direction)
            }
        })
    }*/
}