package com.example.scrollingreccyclerview.userdetail

import com.example.scrollingreccyclerview.api.ApiInterface
import com.example.scrollingreccyclerview.models.DataModel
import retrofit2.Response

class UserDetailRepository(var apiInterface: ApiInterface) {
    suspend fun userData(hashMap: HashMap<String,Int>): Response<DataModel>{
        return apiInterface.userData(hashMap)
    }
}