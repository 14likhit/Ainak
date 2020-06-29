package com.example.ainak.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.ainak.data.models.ImageResponseBody
import com.example.ainak.data.models.Photo


internal class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var imageResponseBodyLiveData: MutableLiveData<ImageResponseBody>
    private lateinit var imageResponseBodyLiveDataError: MutableLiveData<String>

    private var selectedImagePosition: Int = -1
    private var totalPage: Int = -1

    private lateinit var mainImageList: ArrayList<Photo>

}