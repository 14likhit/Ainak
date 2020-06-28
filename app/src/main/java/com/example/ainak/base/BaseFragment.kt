package com.example.ainak.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Parent Fragment for all fragments.
 */
abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    open fun initViews(view: View) {

    }

    fun getBaseActivity(): BaseActivity? {
        return activity as BaseActivity?
    }

    fun showMessage(message: String) {
        if (getBaseActivity() != null) {
            Toast.makeText(getBaseActivity(), message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showMessage(messageResId: Int) {
        if (getBaseActivity() != null) {
            Toast.makeText(
                getBaseActivity(),
                getBaseActivity()!!.getString(messageResId),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun onBackPressed(): Boolean {
        return false
    }

}