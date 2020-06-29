package com.example.ainak.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
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
    private lateinit var searchView: SearchView

    private var searchText: String? = null
    private var currentPage: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        setupToolbar(getString(R.string.app_name), false)

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
            showNoRecordsLayout(false)
        })

        homeViewModel.imageResponseBodyLiveDataError.observe(this, Observer { imageResponse ->
            showNoRecordsLayout(true)
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


        searchText = ImagesRequestBody.FLICKR_IMAGES_INITIAL_SEARCH_TEXT

        showNoRecordsLayout(false)


        activityHomeBinding.homeImageListLayout.loadingAnimation.visibility = View.VISIBLE

        if (homeViewModel.getImagesList() != null) {
            setImages()
        } else {
            homeViewModel.getImages(
                ImagesRequestBody.initialImageRequest()
            )
        }

        activityHomeBinding.homeNoRecordsFoundLayout.retryButton.setOnClickListener(View.OnClickListener {
            if (isLoading) {
                loadMoreImageRequest(currentPage!!)
            } else {
                homeViewModel.getImages(
                    ImagesRequestBody.initialImageRequest()
                )
            }
        })

    }


    private fun showNoRecordsLayout(hasToShow: Boolean) {
        activityHomeBinding.homeImageListLayout.imageListRelativeLayout.visibility =
            if (hasToShow) View.GONE else View.VISIBLE
        activityHomeBinding.homeNoRecordsFoundLayout.noRecordsFoundConstraintLayout.visibility =
            if (hasToShow) View.VISIBLE else View.GONE
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
        currentPage = page
        homeViewModel.getImages(
            getImageRequestBody(
                searchText!!,
                page
            )
        )
    }

    private fun getImageRequestBody(searchQuery: String, pages: Int): ImagesRequestBody {
        return ImagesRequestBody(searchQuery, pages)
    }

    private fun setImages() {
        images = homeViewModel.getImagesList()
        homeViewModel.mainImageList = images
        imageListAdapter!!.images = images
        imageListAdapter!!.notifyDataSetChanged()
        activityHomeBinding.homeImageListLayout.loadingAnimation.visibility = View.GONE
    }

    private fun addImages() {
        images!!.addAll(homeViewModel.getImagesList()!!)
        homeViewModel.mainImageList!!.addAll(images!!)
        imageListAdapter!!.addItems(homeViewModel.getImagesList())
        activityHomeBinding.homeImageListLayout.loadingAnimation.visibility = View.GONE
    }

    private fun addLoading() {
        isLoading = true
        imageListAdapter!!.addLoading()
    }

    private fun removeLoading() {
        isLoading = false
        imageListAdapter!!.removeLoading()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        val searchPlate =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        searchPlate.hint = "Search"
        val searchPlateView: View =
            searchView.findViewById(androidx.appcompat.R.id.search_plate)
        searchPlateView.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )

        searchView.setOnSearchClickListener {
            val view = findViewById<TextView>(R.id.toolbar_title_text_view)
            view.visibility = View.GONE
        }


        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                val view = findViewById<TextView>(R.id.toolbar_title_text_view)
                view.visibility = View.VISIBLE
                return false
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fetchSearchResult(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return super.onCreateOptionsMenu(menu)
    }

    private fun fetchSearchResult(query: String?) {
        if (images != null && images!!.size > 0) {
            imageListAdapter!!.clear();
            imageListAdapter!!.addLoading()
            searchText = query
            homeViewModel.getImages(getImageRequestBody(searchText!!, 1))
        }
    }

    override fun onItemClick(item: Photo, position: Int, view: View?) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val newFragment: SlideShowDialogFragment = SlideShowDialogFragment.newInstance()
        homeViewModel.selectedImagePosition = position
        newFragment.show(ft, newFragment.tag)
    }
}

