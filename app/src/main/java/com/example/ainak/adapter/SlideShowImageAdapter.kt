package com.example.ainak.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.example.ainak.R
import com.example.ainak.data.models.Photo
import com.example.ainak.databinding.ImageFullScreenPreviewBinding
import com.example.ainak.utils.Utils


class SlideShowImageAdapter(private var activity: Activity, private var images: ArrayList<Photo>) :
    PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null

    private lateinit var binding: ImageFullScreenPreviewBinding

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater =
            activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val view: View =
            layoutInflater!!.inflate(R.layout.image_full_screen_preview, container, false)
        binding = DataBindingUtil.bind(view)!!

        Utils.loadImage(
            activity,
            images[position].imageUrl,
            R.drawable.ic_logo,
            R.drawable.ic_logo,
            binding.photoImageView
        )

        container.addView(view)

        return view
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj as View
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}