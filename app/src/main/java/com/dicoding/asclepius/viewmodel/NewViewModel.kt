package com.dicoding.asclepius.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.api.ApiClient
import com.dicoding.asclepius.api.NewsItem
import com.dicoding.asclepius.response.CancerNewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewViewModel : ViewModel() {

    private val _newsList = MutableLiveData<List<NewsItem>>()
    val newsList: LiveData<List<NewsItem>> = _newsList

    private val _loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _loading

    fun getHealthNews(onSuccess: (List<NewsItem>) -> Unit, onFailure: (String) -> Unit) {
        _loading.value = true
        ApiClient.getApiService.searchHealthNews("cancer", "health", "en", ApiClient.API_KEY)
            .enqueue(object : Callback<CancerNewsResponse> {
                override fun onResponse(
                    call: Call<CancerNewsResponse>,
                    response: Response<CancerNewsResponse>
                ) {
                    if (response.isSuccessful) {
                        _loading.value = false
                        val articles = response.body()?.articles ?: emptyList()
                        val newsList = articles.mapNotNull { article ->
                            if (!article.title.isNullOrEmpty() && !article.urlToImage.isNullOrEmpty() && !article.url.isNullOrEmpty()) {
                                NewsItem(article.title, article.urlToImage, article.url)
                            } else {
                                null
                            }
                        }
                        onSuccess(newsList)
                    } else {
                        _loading.value = false
                        onFailure("Failed to fetch news")
                    }
                }
                override fun onFailure(call: Call<CancerNewsResponse>, t: Throwable) {
                    _loading.value = false
                    onFailure(t.message ?: "Unknown error")
                }
            })
    }

    fun fetchHealthNews() {
        getHealthNews(
            onSuccess = { newsList ->
                _newsList.postValue(newsList)
            },
            onFailure = {"gagal memuat"
            }
        )
    }
}