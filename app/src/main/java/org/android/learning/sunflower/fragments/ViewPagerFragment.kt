package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.android.learning.sunflower.R
import org.android.learning.sunflower.adapters.PagerAdapter
import org.android.learning.sunflower.adapters.Tab
import org.android.learning.sunflower.databinding.FragmentViewPagerBinding

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        binding.apply {
            viewPager.adapter = PagerAdapter(this@ViewPagerFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.setIcon(getTabIcon(Tab.values()[position]))
                tab.text = getTabTitle(Tab.values()[position])
            }.attach()
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarViewPager)
        return binding.root
    }

    private fun getTabIcon(tab: Tab): Int = when (tab) {
        Tab.MY_GARDEN -> R.drawable.tab_garden_ic_selector
        Tab.PLANTS -> R.drawable.tab_plants_ic_selector
    }

    private fun getTabTitle(tab: Tab): String = when (tab) {
        Tab.MY_GARDEN -> getString(R.string.garden_tab_title)
        Tab.PLANTS -> getString(R.string.plants_tab_title)
    }

}