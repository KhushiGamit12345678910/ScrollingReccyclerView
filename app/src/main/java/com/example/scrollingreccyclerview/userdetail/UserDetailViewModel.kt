package com.example.scrollingreccyclerview.userdetail

import android.app.Application

import android.util.Log
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.MutableLiveData
import com.example.scrollingreccyclerview.R
import com.example.scrollingreccyclerview.api.ApiInterface
import com.example.scrollingreccyclerview.api.RetrofitClient
import com.example.scrollingreccyclerview.const.PAGE
import com.example.scrollingreccyclerview.models.DataModel
import com.example.scrollingreccyclerview.ui.Resource
import com.example.scrollingreccyclerview.ui.ResponseCodeCheck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.create

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var userDetailRepository: UserDetailRepository

    var responseCodeCheck: ResponseCodeCheck = ResponseCodeCheck()
    var mutableLiveData: MutableLiveData<Resource<DataModel>> = MutableLiveData()
    var liveData: MutableLiveData<Resource<DataModel>> = mutableLiveData

    fun userDetail(page : Int) {

        val hashMap: HashMap<String, Int> = HashMap()
        hashMap.apply {
            put(PAGE, page)

        }

        mutableLiveData.value = Resource.loading(null)

        val apiInterface = RetrofitClient.getRetrofit().create(ApiInterface::class.java)
        userDetailRepository = UserDetailRepository(apiInterface)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository : Response<DataModel> = userDetailRepository.userData(hashMap)
                mutableLiveData.postValue(responseCodeCheck.getResponseResult(repository))

            }catch (e : Exception){

                //Log.d("data_info", e.message.toString())
                liveData.postValue(Resource.error(getApplication<Application>().getString(R.string.something_went_wrong), null))
            }
        }
    }


}