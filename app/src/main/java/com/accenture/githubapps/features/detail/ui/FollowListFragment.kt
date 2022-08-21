package com.accenture.githubapps.features.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.accenture.githubapps.databinding.FragmentListBinding
import com.accenture.githubapps.di.Injectable
import com.accenture.githubapps.di.injectViewModel
import com.accenture.githubapps.extension.gone
import com.accenture.githubapps.extension.visible
import com.accenture.githubapps.utils.HelperLoading
import dagger.android.AndroidInjection
import javax.inject.Inject

class FollowListFragment(
    private val isFollower: Boolean,
    private val userName: String,
): Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var detailViewModel: DetailViewModel

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FollowAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
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
        binding.apply {
            progressBar.visible()
            detailViewModel.getFollowerFavoriteUser(userName).observe(viewLifecycleOwner) { result ->
                progressBar.gone()
                if (result.isNotEmpty()) {
                    // ambil dari room db
                    adapter.setList(result)
                } else {
                    // ambil dari API
                    detailViewModel.setUserFollow(isFollower, userName)
                    getAllFollower()
                }
            }
        }
    }

    private fun getAllFollower() {
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