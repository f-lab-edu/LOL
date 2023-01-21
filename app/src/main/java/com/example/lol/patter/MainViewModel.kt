package com.example.lol.patter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lol.retrofit.LOLResponse.LOLResponseItem
import com.example.lol.retrofit.LOLService
import com.example.lol.retrofit.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _items = MutableLiveData<ArrayList<LOLResponseItem>>()
    val items: LiveData<ArrayList<LOLResponseItem>>
        get() = _items

    private val _fail = MutableLiveData<Boolean>()
    val fail: LiveData<Boolean>
        get() = _fail

    init {
        _items.value = arrayListOf()
        _fail.value = false
    }

    fun loadUserInfo() {
        uiScope.launch {
            val retrofitAPI = RetrofitConnection.getInstance().create(LOLService::class.java)
            retrofitAPI.getInformation(
                //api 요청이 실패한다면 인증키 유효기간이 지났기 때문(인증키 유효기간 하루)
                "RGAPI-51ba6f6b-c589-4fc0-8ba6-3c3668761edd"
            ).enqueue(object : Callback<List<LOLResponseItem>> {
                override fun onResponse(
                    call: Call<List<LOLResponseItem>>,
                    response: Response<List<LOLResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        Log.e("api_test", _items.value.toString())
                        response.body()?.let { _items.value = it as ArrayList<LOLResponseItem> }
                    } else {
                        _fail.value = true
                    }
                }

                override fun onFailure(call: Call<List<LOLResponseItem>>, t: Throwable) {
                    Log.e("가져오기 실패", "실패?")
                    t.printStackTrace()
                }
            })
        }
    }
}