package com.example.ainak.ui.home

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ainak.R
import com.example.ainak.adapter.ImageListAdapter
import com.example.ainak.base.BaseActivity
import com.example.ainak.customlisteners.OnItemClickListener
import com.example.ainak.customlisteners.RecyclerViewPaginator
import com.example.ainak.data.models.ImagesRequestBody
import com.example.ainak.data.models.Photo
import com.example.ainak.data.remote.RemoteRepositoryClass
import com.example.ainak.databinding.ActivityHomeBinding
import com.example.ainak.network.ApiClient
import com.example.ainak.ui.slideshow.SlideShowDialogFragment
import com.example.ainak.viewmodelfactories.HomeViewModelFactory
import com.example.ainak.viewmodels.HomeViewModel

class HomeActivity : BaseActivity(), OnItemClickListener<Photo> {

    private lateinit var activityHomeBinding: ActivityHomeBinding

    private lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    private var images: ArrayList<Photo>? = null
    private var imageListAdapter: ImageListAdapter? = null

    private var imagesLayoutManager: GridLayoutManager? = null

    private var isLoading: Boolean = false

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
        homeViewModel.imageResponseBodyLiveData.observe(this, Observer { imageResponse ->
            if (imageResponse != null) {
                if (isLoading) {
                    removeLoading()
                    addImages()
                } else {
                    setImages()
                }
            }
        })
    }


    private fun initView() {
        if (imageListAdapter == null) {
            imageListAdapter = ImageListAdapter(this, this)
        }

        if (images == null) {
            images = ArrayList()
        }

        imagesLayoutManager = GridLayoutManager(this, 2)

        activityHomeBinding.homeImageListLayout.imageListRecyclerView.layoutManager =
            imagesLayoutManager
        activityHomeBinding.homeImageListLayout.imageListRecyclerView.itemAnimator =
            DefaultItemAnimator()
        setScrollListener()
        activityHomeBinding.homeImageListLayout.imageListRecyclerView.adapter = imageListAdapter

        if (homeViewModel.getImagesList() != null) {
            setImages()
        } else {
            homeViewModel.getImages(
                ImagesRequestBody.initialImageRequest()
            )
        }

    }

    private fun setScrollListener() {
        activityHomeBinding.homeImageListLayout.imageListRecyclerView.addOnScrollListener(object :
            RecyclerViewPaginator(activityHomeBinding.homeImageListLayout.imageListRecyclerView) {
            override fun isLastPage(): Boolean {
                return false
            }

            override fun loadMore(start: Long?, count: Long?) {
                addLoading()
                loadMoreImageRequest(count!!.toInt())
            }

        })
    }

    private fun loadMoreImageRequest(page: Int) {
        homeViewModel.getImages(
            getImageRequestBody(
                ImagesRequestBody.FLICKR_IMAGES_INITIAL_SEARCH_TEXT,
                page
            )
        )
    }

    private fun getImageRequestBody(searchQuery: String, pages: Int): ImagesRequestBody {
        return ImagesRequestBody(searchQuery, pages)
    }


    private fun setImages() {
        images = homeViewModel.getImagesList()
        imageListAdapter!!.images = images
        imageListAdapter!!.notifyDataSetChanged()
    }

    private fun addImages() {
        images!!.addAll(homeViewModel.getImagesList()!!)
        imageListAdapter!!.addItems(homeViewModel.getImagesList())
    }

    private fun addLoading() {
        isLoading = true
        imageListAdapter!!.addLoading()
    }

    private fun removeLoading() {
        isLoading = false
        imageListAdapter!!.removeLoading()
    }

    override fun onItemClick(item: Photo, position: Int, view: View?) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val newFragment: SlideShowDialogFragment = SlideShowDialogFragment.newInstance()
        newFragment.show(ft, newFragment.tag)
    }
}
