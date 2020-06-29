package com.example.ainak.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ainak.R
import com.example.ainak.customlisteners.OnItemClickListener
import com.example.ainak.data.models.Photo
import com.example.ainak.databinding.LayoutImageItemBinding
import com.example.ainak.utils.Utils

/**
 * Adapter for Image List
 */

class ImageListAdapter(
    private val mContext: Context,
    private val onItemClickListener: OnItemClickListener<Photo>
) :
    RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder>() {

    var images: MutableList<Photo>? = null

    var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        return ImageListViewHolder(
            LayoutImageItemBinding.inflate(
                layoutInflater!!,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (images == null) 0 else images?.size!!
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
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

    class ImageListViewHolder(val binding: LayoutImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}