package com.example.scrollingreccyclerview.models

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

class DataModel(
    @SerializedName("status")var status : Boolean? = null,
    @SerializedName("message")var message : String? = null,
    @SerializedName("data")var data : List<Data>? = null

)
class Data(
    @SerializedName("id")var id : String? = null,
    @SerializedName("name")var name : String? = null,
    @SerializedName("email")var email : String? = null,
    @SerializedName("profile_picture")var profile_picture : String? = null

) : java.io.Serializable