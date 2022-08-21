package com.accenture.githubapps.features.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.accenture.githubapps.R
import com.accenture.githubapps.databinding.FragmentDetailBinding
import com.accenture.githubapps.databinding.ToolbarBinding
import com.accenture.githubapps.di.Injectable
import com.accenture.githubapps.di.injectViewModel
import com.accenture.githubapps.extension.inVisible
import com.accenture.githubapps.extension.setCustomToolbar
import com.accenture.githubapps.extension.visible
import com.accenture.githubapps.utils.HelperLoading
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import java.lang.Exception
import javax.inject.Inject

class DetailFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var detailViewModel: DetailViewModel

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding

    private lateinit var username: String

    private val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root
        setHasOptionsMenu(true)
        initToolbar()

        detailViewModel = injectViewModel(viewModelFactory)

        username = args.username

        setUserDetail()

        binding.apply {
            val viewPager: ViewPager = viewPager
            val tabLayout: TabLayout = tabs

            setupViewPager(viewPager)
            tabLayout.setupWithViewPager(viewPager)
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                if (!findNavController().popBackStack()) activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        toolbarBinding.toolbar.title = "Detail"
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black)

        setCustomToolbar(toolbar)
    }

    private fun setUserDetail() {
        try {
            binding.apply {
                imgAvatar.inVisible()
                imgFollowers.inVisible()
            }
            HelperLoading.displayLoadingWithText(requireContext(), "", false)

            detailViewModel.setUserDetail(username)

            getUserDetail()
        } catch (err: Exception) {
            HelperLoading.hideLoading()
            Toast.makeText(requireContext(), "Error : ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun getUserDetail() {
        try {
            detailViewModel.getUserDetail().observe(viewLifecycleOwner) { result ->
                binding.apply {
                    HelperLoading.hideLoading()
                    imgAvatar.visible()
                    imgFollowers.visible()

                    tvUsername.text = result.login.ifEmpty { "" }
                    tvName.text = result.name?.ifEmpty { "" }

                    tvFollowers.text = "Followers ${result.followers.toString().ifEmpty{ "" }}"
                    tvFollowing.text = "Following ${result.following.toString().ifEmpty{ "" }}"

                    Glide.with(root)
                        .load(result.avatar_url)
                        .centerCrop()
                        .into(imgAvatar)
                }
            }
        } catch (err: Exception) {
            HelperLoading.hideLoading()
            Toast.makeText(requireContext(), "Error : ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerCustomerListAdapter(childFragmentManager)

        adapter.addFragment(FollowListFragment("Followers", username), "Followers")
        adapter.addFragment(FollowListFragment("Following", username), "Following")
        viewPager.adapter = adapter
    }

    private class ViewPagerCustomerListAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }
}