package com.maximvs.trackingtravel.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.maximvs.trackingtravel.R
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.databinding.ActivityMainBinding
import com.maximvs.trackingtravel.view.fragments.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initNavigation()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, RouteFragment())
            .commit()
    }

    fun startRequestFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, RequestFragment())
            .addToBackStack(null)
            .commit()
    }

    fun startRouteFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, RouteFragment())
            .addToBackStack(null)
            .commit()
    }

    fun launchDetailsFragment(route: Route) {
        val bundle = Bundle()
        bundle.putParcelable("route", route)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun removeDetailsFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, RouteFragment())
            .addToBackStack(null)
            .commit()
    }

    fun initNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                com.maximvs.trackingtravel.R.id.route -> {
                    val tag = "route"
                    val fragment = checkFragmentExistence(tag)
                    //В первом параметре, если фрагмент не найден и метод вернул null, то с помощью
                    //элвиса мы вызываем создание нвого фрагмента
                    changeFragment(fragment ?: RouteFragment(), tag)
                    true
                }
                R.id.settings -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SettingsFragment(), tag)
                    true
                }
                R.id.favorites -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FavoritesFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    //Ищем фрагмент по тэгу, если он есть то возвращаем его, если нет - то null
    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}
