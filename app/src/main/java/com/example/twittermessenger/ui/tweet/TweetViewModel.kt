package com.example.twittermessenger.ui.tweet


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.models.HomeTimelineModel
import com.example.domain.usecases.GetHomeTimelineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetViewModel @Inject constructor(
    private val getHomeTimelineUseCase: GetHomeTimelineUseCase
): ViewModel() {

    private val _homeTimeline = MutableLiveData<Resource<HomeTimelineModel>>()
    val homeTimeline : LiveData<Resource<HomeTimelineModel>>get() = _homeTimeline

   private  fun fetchHomeTimeline() {
         _homeTimeline.value = Resource.loading(null)

         viewModelScope.launch{
             _homeTimeline.value =  getHomeTimelineUseCase()
         }
    }

    fun refreshHomeTimelineData() {
        fetchHomeTimeline()
    }
}