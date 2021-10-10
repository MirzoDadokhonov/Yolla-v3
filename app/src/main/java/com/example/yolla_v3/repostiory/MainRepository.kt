package com.example.yolla_v3.repostiory

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


class MainRepository(context : Context) {
    private val FILE_NAME = "MyYollaV3SharedPrefns"
    private val TAG_FOR_JSON_CONTACTS = "MyYollaV3ContactsTag005"

    private val sharedPreferences : SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    }

    fun arrayToJsonString(a : ArrayList<ItemData>) {
        val jsonContacts = Gson().toJson(a)
        putString(TAG_FOR_JSON_CONTACTS, jsonContacts)
    }

    fun jsonStringToArray() : ArrayList<ItemData> {
        getString(TAG_FOR_JSON_CONTACTS)?.let {
            return Gson().fromJson(it, Array<ItemData>::class.java).toCollection(ArrayList())
        }
        return ArrayList()
    }

    fun putString(key : String, value : String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key : String) : String? {
        return sharedPreferences.getString(key, null)
    }


}