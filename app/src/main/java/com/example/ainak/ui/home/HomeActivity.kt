package com.example.ainak.ui.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ainak.R
import com.example.ainak.adapter.ImageListAdapter
import com.example.ainak.base.BaseActivity
import com.example.ainak.data.models.Photo
import com.example.ainak.data.remote.RemoteRepositoryClass
import com.example.ainak.databinding.ActivityHomeBinding
import com.example.ainak.network.ApiClient
import com.example.ainak.viewmodelfactories.HomeViewModelFactory
import com.example.ainak.viewmodels.HomeViewModel

class HomeActivity : BaseActivity() {

    private lateinit var activityHomeBinding: ActivityHomeBinding

    private lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    private var images: ArrayList<Photo>? = null
    private var imageListAdapter: ImageListAdapter? = null

    private var imagesLayoutManager: GridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        homeViewModelFactory =
            HomeViewModelFactory(
                RemoteRepositoryClass.getInstance(ApiClient.getInstance()?.getAinakApi()!!)!!
            )

        homeViewModel =
            ViewModelProvider(
                this,
                homeViewModelFactory
            ).get(HomeViewModel::class.java)

        addObservers()

        initView()

    }

    private fun addObservers() {

    }

    private fun initView() {
        if (imageListAdapter == null) {
            imageListAdapter = ImageListAdapter(this)
        }

        if (images == null) {
            images = ArrayList()
        }

        imagesLayoutManager = GridLayoutManager(this, 2)

        activityHomeBinding.homeImageListLayout.imageListRecyclerView.layoutManager =
            imagesLayoutManager
        activityHomeBinding.homeImageListLayout.imageListRecyclerView.itemAnimator =
            DefaultItemAnimator()
        activityHomeBinding.homeImageListLayout.imageListRecyclerView.adapter = imageListAdapter

    }
}
