package org.android.learning.sunflower.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.android.learning.sunflower.fragments.GardenFragment
import org.android.learning.sunflower.fragments.PlantsFragment

enum class Tab(val index: Int) {
    MY_GARDEN(values().indexOf(MY_GARDEN)),
    PLANTS(values().indexOf(PLANTS))
}

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping ViewPager page indexes(tabs) to the corresponding fragment constructors
     */
    private val tabToFragments: Map<Tab, () -> Fragment> = mapOf(
        Tab.MY_GARDEN to { GardenFragment() },
        Tab.PLANTS to { PlantsFragment() }
    )

    override fun createFragment(position: Int): Fragment =
        tabToFragments[Tab.values()[position]]?.invoke() ?: throw IndexOutOfBoundsException()

    override fun getItemCount() = Tab.values().size

}