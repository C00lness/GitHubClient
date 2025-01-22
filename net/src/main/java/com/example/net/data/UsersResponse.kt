package com.example.net.data
import com.google.gson.annotations.SerializedName


data class UsersResponse (

  @SerializedName("total_count"        ) var totalCount        : Int?             = null,
  @SerializedName("incomplete_results" ) var incompleteResults : Boolean?         = null,
  @SerializedName("items"              ) var items             : ArrayList<UserItems> = arrayListOf()

)