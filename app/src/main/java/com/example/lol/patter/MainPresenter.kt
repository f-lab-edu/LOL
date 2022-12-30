package com.example.lol.patter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.lol.retrofit.LOLResponse.LOLResponseItem
import com.example.lol.retrofit.LOLService
import com.example.lol.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    override fun loadUserInfo() {
        val retrofitAPI = RetrofitConnection.getInstance().create(LOLService::class.java)
        retrofitAPI.getInformation(
            //api 요청이 실패한다면 인증키 유효기간이 지났기 때문(인증키 유효기간 하루)
            "RGAPI-c2953966-ad8f-427a-9bd9-844cf9521cdf"
        ).enqueue(object : Callback<List<LOLResponseItem>> {
            override fun onResponse(
                call: Call<List<LOLResponseItem>>,
                response: Response<List<LOLResponseItem>>
            ) {
                if (response.isSuccessful) {
                    view.setToast("데이터 가져오기 성공!")
                    response.body()?.let { view.setUI(it as ArrayList<LOLResponseItem>) }
                } else {
                    view.setToast("데이터 가져오기 실패!")
                }
            }

            override fun onFailure(call: Call<List<LOLResponseItem>>, t: Throwable) {
                Log.e("가져오기 실패", "실패?")
                t.printStackTrace()
            }
        })
    }
}