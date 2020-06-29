package com.example.ainak.ui.splash

import android.os.Bundle
import com.example.ainak.R
import com.example.ainak.base.BaseActivity
import com.example.ainak.utils.ActivityLauncher
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    private val timerValueSeconds: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startTimer()
    }

    private fun startTimer() {
        Observable.timer(timerValueSeconds, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                ActivityLauncher.launchHomeActivity(this@SplashActivity)
                finish()
            }
    }
}
