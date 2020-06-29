package com.example.ainak.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ainak.R
import com.example.ainak.customlisteners.OnItemClickListener
import com.example.ainak.data.models.Photo
import com.example.ainak.databinding.LayoutImageItemBinding
import com.example.ainak.databinding.LayoutLoadingBinding
import com.example.ainak.utils.Utils


/**
 * Adapter for Image List
 */

class ImageListAdapter(
    private val mContext: Context,
    private val onItemClickListener: OnItemClickListener<Photo>
) :
    RecyclerView.Adapter<ViewHolder>() {

    var images: MutableList<Photo>? = null

    var layoutInflater: LayoutInflater? = null

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1
    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return when (viewType) {
            VIEW_TYPE_NORMAL -> ImageListViewHolder(
                LayoutImageItemBinding.inflate(
                    layoutInflater!!,
                    parent,
                    false
                )
            )
            VIEW_TYPE_LOADING -> ImageLoadingViewHolder(
                LayoutLoadingBinding.inflate(
                    layoutInflater!!,
                    parent,
                    false
                )
            )
            else -> ImageListViewHolder(
                LayoutImageItemBinding.inflate(
                    layoutInflater!!,
                    parent,
                    false
                )
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == images!!.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int {
        return if (images == null) 0 else images?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ImageListViewHolder) {
            if (images != null) {
                var photo = images!![position]
                Utils.loadImage(
                    mContext,
                    photo.imageUrl,
                    R.drawable.ic_logo,
                    R.drawable.ic_logo,
                    holder.binding.imageItemView
                )

                holder.binding.imageSquareLayout.setOnClickListener {
                    onItemClickListener.onItemClick(
                        photo,
                        position,
                        holder.itemView
                    )
                }
            }
        }
    }

    fun addItems(photos: List<Photo>?) {
        images!!.addAll(photos!!)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        images!!.add(Photo())
        notifyItemInserted(images!!.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position: Int = images!!.size - 1
        val item: Photo? = getItem(position)
        if (item != null) {
            images!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        images!!.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Photo? {
        return images!!.get(position)
    }

    class ImageListViewHolder(val binding: LayoutImageItemBinding) :
        ViewHolder(binding.root) {

    }

    class ImageLoadingViewHolder(val binding: LayoutLoadingBinding) :
        ViewHolder(binding.root) {

    }
}