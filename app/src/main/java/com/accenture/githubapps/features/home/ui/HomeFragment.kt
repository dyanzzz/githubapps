package com.accenture.githubapps.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.databinding.FragmentHomeBinding
import com.accenture.githubapps.di.Injectable
import com.accenture.githubapps.di.injectViewModel
import com.accenture.githubapps.utils.HelperLoading
import com.google.android.material.tabs.TabLayout
import timber.log.Timber
import javax.inject.Inject

class HomeFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HomeAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var limit: Int = 10
    private var page: Int = 1
    private var totalPage: Int = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        homeViewModel = injectViewModel(viewModelFactory)

        adapter = HomeAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = FragmentHomeBinding.bind(view)

        setDataListPopular()

        binding.apply {
            layoutManager = LinearLayoutManager(activity)
            rv.setHasFixedSize(true)
            rv.layoutManager = layoutManager
            rv.adapter = adapter

            /*rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    val total  = adapter.itemCount

                    if (!isLoading && page < totalPage){
                        if (visibleItemCount + pastVisibleItem >= total){
                            page++

                            homeViewModel.setListPopular()
                            getDataListPopular()
                        }
                    }

                }
            })*/

            swipe.setOnRefreshListener {
                swipe.isRefreshing= true
                page = 1

                try {
                    adapter.clear()

                    if (tabLayout.selectedTabPosition== 0) {
                        setDataListPopular()
                    } else {
                        setDataListFavorite()
                    }
                    swipe.isRefreshing= false
                } catch (e: Exception){
                    Timber.e("Error setListSs : $e")
                    Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
                }
            }

            tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    adapter.clear()
                    if (tab?.position == 0){
                        setDataListPopular()
                    } else {
                        setDataListFavorite()
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }
            })
        }
    }

    private fun setDataListFavorite() {
        adapter.clear()
        homeViewModel.getAllFavoriteUsers().observe(viewLifecycleOwner) { result ->
            Timber.e(result.toString())
            binding.rv.visibility = View.VISIBLE
            adapter.setList(result)
        }
    }

    private fun setDataListPopular() {
        try {
            adapter.clear()
            page = 1

            HelperLoading.displayLoadingWithText(requireContext(), "", false)

            homeViewModel.getCheckFavoriteUsers().observe(viewLifecycleOwner) { userName ->
                if(userName.isNotEmpty()) {
                    homeViewModel.getRemoveFavoriteUsers(userName[0])
                }
            }
            homeViewModel.setListPopular()

            getDataListPopular()

            setOnClick()
        } catch (e: Exception){
            Timber.e("Error setListSs : $e")
            Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataListPopular() {
        try {
            isLoading = true
            homeViewModel.getListPopular().observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    Timber.e(result.toString())
                    HelperLoading.hideLoading()

                    totalPage = 0

                    binding.rv.visibility = View.VISIBLE

                    adapter.setList(result)

                    isLoading = false
                    Timber.d("###-- Success get List")
                } else {
                    HelperLoading.hideLoading()
                    binding.rv.visibility = View.GONE
                    Toast.makeText(requireContext(), "Data Not Found", Toast.LENGTH_LONG).show()
                }
            }
        }catch (err : Exception){
            HelperLoading.hideLoading()
            isLoading = false
            Toast.makeText(requireContext(), "Error : ${err.message}", Toast.LENGTH_LONG).show()
            Timber.d("###-- Error get List")
        }
    }

    private fun setOnClick() {
        adapter.setHomeCallback(object : HomeCallback {
            override fun onClick(data: User) {
                val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data.login)
                requireView().findNavController().navigate(direction)
            }
        })
    }
}