package com.example.ainak.base

import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.ainak.R
import com.example.ainak.utils.Utils

/**
 * Parent Activity for all the Activities.
 */
abstract class BaseActivity : AppCompatActivity() {

    fun setupToolbar(title: String?, homeButtonEnable: Boolean) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar) ?: return

        if (!homeButtonEnable) {
            val inset = Utils.dpToPx(this, 16F).toInt()
            toolbar.setContentInsetsAbsolute(inset, inset)
        }

        val titleTextView = findViewById<TextView>(R.id.toolbar_title_text_view)
        titleTextView.text = title

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(homeButtonEnable)
            actionBar.setDisplayShowHomeEnabled(homeButtonEnable)

        }
    }

    fun getCurrentFragment(): BaseFragment? {
        return supportFragmentManager.findFragmentById(R.id.container) as BaseFragment?
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (getCurrentFragment() == null || !getCurrentFragment()!!.onBackPressed()) {
            super.onBackPressed()
        }
    }

    // Fragment Related
    fun replaceFragment(fragment: Fragment, tag: String, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }


    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(messageResId: Int) {
        Toast.makeText(
            this, this.getString(messageResId), Toast.LENGTH_SHORT
        ).show()
    }

}