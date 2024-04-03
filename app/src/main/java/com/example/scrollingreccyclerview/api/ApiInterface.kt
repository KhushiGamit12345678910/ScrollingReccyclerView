package com.example.scrollingreccyclerview.api

import com.example.scrollingreccyclerview.models.DataModel
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("users.php")
    suspend fun userData(
        @FieldMap hashMap: HashMap<String,Int>
    ): Response<DataModel>

}