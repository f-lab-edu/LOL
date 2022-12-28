package com.example.lol.patter

import android.content.Context
import com.example.lol.retrofit.LOLResponse.LOLResponseItem
import retrofit2.Response

interface MainContract {
    interface View {
//        fun getItem(response: Response<List<LOLResponseItem>>)
        fun setUI(lolData: ArrayList<LOLResponseItem>)
        fun setToast(message: String)
    }

    interface Presenter {
        fun getAPI()
    }
}