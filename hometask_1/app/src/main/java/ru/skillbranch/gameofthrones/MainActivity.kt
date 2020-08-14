package ru.skillbranch.gameofthrones

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var navController: NavController
    lateinit var mainView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mainView = findViewById<View>(android.R.id.content)
//        initViewModel()
//        savedInstanceState ?: prepareData()
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private fun prepareData() {
        viewModel.syncDataIfNeed().observe(this, Observer <LoadResult<Boolean>>{
            when(it) {
                is LoadResult.Loading -> {
                    navController.navigate(R.id.nav_splash)
                }
                is LoadResult.Sucess -> {
                    val action = NavSplashDirections.actionHavSplashToNavHouses()
                    navController.navigate(action)
                }
                is LoadResult.Error -> {
                    Snackbar.make(mainView, it.errorMsg.toString(), Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        })
    }
}