package energyto.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import energyto.fragment.HomeFragment
import energyto.fragment.QuoteFragment
import energyto.fragment.SettingFragment
import energyto.widget.MTab
import ir.mahdidrv.energyto.R

/*

  this is my first application in android platform :)))
  checkout my blog: mahdidrv.ir

*/
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val makeTab = MTab(this,R.id.tabLayout, R.id.viewPager)
        makeTab.add(QuoteFragment().javaClass,"quote")
        makeTab.add(HomeFragment().javaClass,"home")
        makeTab.add(SettingFragment().javaClass,"setting")
        makeTab.defalutTab(2)

    }
}