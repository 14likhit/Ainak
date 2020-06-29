package com.example.ainak.customlisteners

import android.view.View

/**
 * Custom Listener for Recycler view Adapter
 */
interface OnItemClickListener<T> {
    fun onItemClick(item: T, position: Int, view: View?)
}