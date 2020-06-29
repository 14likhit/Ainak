package com.example.ainak.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ainak.data.models.ImageResponseBody
import com.example.ainak.data.models.ImagesRequestBody
import com.example.ainak.data.models.Photo
import com.example.ainak.data.remote.RemoteRepositoryClass
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * ViewModel to handle home activity images.
 */
class HomeViewModel(private val remoteRepositoryClass: RemoteRepositoryClass) : ViewModel() {

    private val disposables = CompositeDisposable()

    val imageResponseBodyLiveData: MutableLiveData<ImageResponseBody> =
        MutableLiveData<ImageResponseBody>()

    val imageResponseBodyLiveDataError: MutableLiveData<String> =
        MutableLiveData<String>()

    fun getImages(imagesRequestBody: ImagesRequestBody) {
        disposables.add(
            remoteRepositoryClass.getImages(imagesRequestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    imageResponseBodyLiveData.value =
                        Gson().fromJson(result, ImageResponseBody::class.java)
                }, { throwable -> imageResponseBodyLiveDataError.value = "Error Loading Data" })
        )
    }

    fun getImagesList(): ArrayList<Photo>? {
        if (imageResponseBodyLiveData.value != null) {
            return imageResponseBodyLiveData.value!!.photos.photo as ArrayList<Photo>
        }
        return null
    }

    override fun onCleared() {
        disposables.clear()
    }

}