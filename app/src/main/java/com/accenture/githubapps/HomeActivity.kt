package com.accenture.githubapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.accenture.githubapps.databinding.ActivityHomeBinding
import com.accenture.githubapps.utils.Tools
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var navController: NavController

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        navController = findNavController(R.id.nav_fragment)

        Tools.setSystemBarColor(this, R.color.midnightBlue, this)
        Tools.setSystemBarLight(this)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}