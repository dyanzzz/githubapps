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
import com.accenture.githubapps.data.model.Follower
import com.accenture.githubapps.databinding.FragmentDetailBinding
import com.accenture.githubapps.databinding.ToolbarBinding
import com.accenture.githubapps.di.Injectable
import com.accenture.githubapps.di.injectViewModel
import com.accenture.githubapps.extension.inVisible
import com.accenture.githubapps.extension.setCustomToolbar
import com.accenture.githubapps.extension.visible
import com.accenture.githubapps.utils.HelperLoading
import com.accenture.githubapps.utils.SnackBarCustom
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import timber.log.Timber
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
                tabs.inVisible()
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
            binding.apply {
                val count = detailViewModel.checkUserDetailFavorite(username)
                var isChecked = false

                if (count > 0) {
                    Timber.e("Get data from room")
                    detailViewModel.getFavoriteUserDetail(username).observe(viewLifecycleOwner){ result ->
                        Timber.e(result.name)
                        HelperLoading.hideLoading()
                        imgAvatar.visible()
                        imgFollowers.visible()
                        tabs.visible()

                        tvUsername.text = result.login.ifEmpty { "" }
                        tvName.text = result.name?.ifEmpty { "" }

                        tvFollowers.text = "Followers ${result.followers.toString().ifEmpty{ "" }}"
                        tvFollowing.text = "Following ${result.following.toString().ifEmpty{ "" }}"

                        Glide.with(root)
                            .load(result.avatar_url)
                            .centerCrop()
                            .into(imgAvatar)

                        toggleFavorite.setOnClickListener {
                            isChecked = !isChecked
                            if (isChecked) {
                                detailViewModel.postUserFavorite(result)
                                saveFollower(true)
                                saveFollower(false)

                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, requireContext(),
                                    "I like ${result.name}",
                                    R.drawable.ic_close_white, R.color.green_500
                                )
                            } else {
                                detailViewModel.removeUserFavorite(username)
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, requireContext(),
                                    "I don't like ${result.name}",
                                    R.drawable.ic_close_white, R.color.red_500
                                )
                            }

                            toggleFavorite.isChecked = isChecked
                        }
                    }
                } else {
                    Timber.e("Get data from API")
                    detailViewModel.getUserDetail().observe(viewLifecycleOwner) { result ->

                        HelperLoading.hideLoading()
                        imgAvatar.visible()
                        imgFollowers.visible()
                        tabs.visible()

                        tvUsername.text = result.login.ifEmpty { "" }
                        tvName.text = result.name?.ifEmpty { "" }

                        tvFollowers.text = "Followers ${result.followers.toString().ifEmpty{ "" }}"
                        tvFollowing.text = "Following ${result.following.toString().ifEmpty{ "" }}"

                        Glide.with(root)
                            .load(result.avatar_url)
                            .centerCrop()
                            .into(imgAvatar)

                        toggleFavorite.setOnClickListener {
                            isChecked = !isChecked
                            if (isChecked) {
                                detailViewModel.postUserFavorite(result)
                                saveFollower(true)
                                saveFollower(false)
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, requireContext(),
                                    "I like ${result.name}",
                                    R.drawable.ic_close_white, R.color.green_500
                                )
                            } else {
                                detailViewModel.removeUserFavorite(username)
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, requireContext(),
                                    "I don't like ${result.name}",
                                    R.drawable.ic_close_white, R.color.red_500
                                )
                            }
                            toggleFavorite.isChecked = isChecked
                        }
                    }
                }

                Timber.e(count.toString())
                if (count > 0) {
                    toggleFavorite.isChecked = true
                    isChecked = true
                } else {
                    toggleFavorite.isChecked = false
                    isChecked = false
                }
            }
        } catch (err: Exception) {
            HelperLoading.hideLoading()
            Toast.makeText(requireContext(), "Error : ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveFollower(isFollower: Boolean) {
        try {
            detailViewModel.setUserFollow(isFollower, username)
            detailViewModel.getUserFollow().observe(viewLifecycleOwner) { result ->
                binding.apply {
                    if (result.isNotEmpty()) {
                        result.forEach { data ->
                            val follower = Follower(
                                id = null,
                                login = data.login,
                                avatar_url = data.avatar_url,
                                gravatar_id = data.gravatar_id,
                                url = data.url,
                                html_url = data.html_url,
                                followers_url = data.followers_url,
                                following_url = data.following_url,
                                gists_url = data.gists_url,
                                starred_url = data.starred_url,
                                subscriptions_url = data.subscriptions_url,
                                organizations_url = data.organizations_url,
                                repos_url = data.repos_url,
                                events_url = data.received_events_url,
                                received_events_url = data.received_events_url,
                                type = data.type,
                                site_admin = data.site_admin,
                                owner = username,
                                isFollower = isFollower
                            )
                            detailViewModel.postFollowerFavoriteUser(follower)
                        }
                    }
                }
            }
        } catch (err: Exception) {
            HelperLoading.hideLoading()
            Toast.makeText(requireContext(), "Error : ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerCustomerListAdapter(childFragmentManager)

        adapter.addFragment(FollowListFragment(true, username), "Followers")
        adapter.addFragment(FollowListFragment(false, username), "Following")
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