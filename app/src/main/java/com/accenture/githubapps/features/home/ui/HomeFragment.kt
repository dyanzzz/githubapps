package com.accenture.githubapps.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.accenture.githubapps.R
import com.accenture.githubapps.databinding.FragmentHomeBinding
import com.accenture.githubapps.databinding.ToolbarSingleBinding
import com.accenture.githubapps.di.Injectable
import com.accenture.githubapps.ui.setCustomToolbar
import javax.inject.Inject

class HomeFragment: Fragment(), Injectable {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarSingleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarSingleBinding.bind(binding.root)
        context ?: return binding.root
        setHasOptionsMenu(true)
        initToolbar()

        return binding.root
    }

    private fun initToolbar() {
        toolbarBinding.titleBar.text = resources.getString(R.string.home)
        val toolbar = toolbarBinding.toolbar
        //toolbar.setNavigationIcon(R.drawable.ic_chevron_left)

        setCustomToolbar(toolbar)
    }
}