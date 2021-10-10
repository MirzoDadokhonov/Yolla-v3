package com.example.yolla_v3.repostiory

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ItemData(@field: SerializedName("name") var name : String,@field: SerializedName("phone_num") var phoneNum : String,@field: SerializedName("status") var status : Boolean?) : Serializable