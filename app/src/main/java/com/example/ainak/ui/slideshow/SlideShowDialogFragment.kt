package com.example.ainak.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.ainak.adapter.SlideShowImageAdapter
import com.example.ainak.data.models.Photo
import com.example.ainak.data.remote.RemoteRepositoryClass
import com.example.ainak.databinding.FragmentSlideShowDialogBinding
import com.example.ainak.network.ApiClient
import com.example.ainak.ui.home.HomeActivity
import com.example.ainak.viewmodelfactories.HomeViewModelFactory
import com.example.ainak.viewmodels.HomeViewModel

/**
 * Dialog Fragment to show selected images in dialog.
 */
class SlideShowDialogFragment : DialogFragment(), ViewPager.OnPageChangeListener {

    companion object {
        const val TAG = "SlideShowDialogFragment"

        fun newInstance(): SlideShowDialogFragment {
            return SlideShowDialogFragment()
        }
    }

    private lateinit var fragmentSlideShowDialogBinding: FragmentSlideShowDialogBinding

    private lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    private var images: ArrayList<Photo>? = null
    private var slideShowImageAdapter: SlideShowImageAdapter? = null

    private lateinit var homeActivity: HomeActivity
    private var currentImage: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSlideShowDialogBinding =
            FragmentSlideShowDialogBinding.inflate(inflater, container, false)

        homeActivity = activity as HomeActivity

        homeViewModelFactory =
            HomeViewModelFactory(
                RemoteRepositoryClass.getInstance(ApiClient.getInstance()?.getAinakApi()!!)!!
            )

        homeViewModel =
            ViewModelProvider(
                homeActivity,
                homeViewModelFactory
            ).get(HomeViewModel::class.java)

        setView()
        return fragmentSlideShowDialogBinding.root
    }

    private fun setView() {
        images = homeViewModel.mainImageList
        currentImage = homeViewModel.selectedImagePosition
        slideShowImageAdapter = SlideShowImageAdapter(activity!!, images!!)
        fragmentSlideShowDialogBinding.slideShowViewPager.adapter = slideShowImageAdapter
        fragmentSlideShowDialogBinding.slideShowViewPager.addOnPageChangeListener(this)
        setCurrentItem()
    }

    private fun setCurrentItem() {
        fragmentSlideShowDialogBinding.slideShowViewPager.setCurrentItem(currentImage, false)
        displayImageInfo(currentImage)
    }

    private fun displayImageInfo(position: Int) {
        fragmentSlideShowDialogBinding.imageTitle.text = images!![position].title
    }

    override fun onPageScrollStateChanged(state: Int) {
        displayImageInfo(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

}
