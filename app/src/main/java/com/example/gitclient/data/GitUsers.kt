package com.example.gitclient.data
import com.google.gson.annotations.SerializedName


data class GitUsers (

  @SerializedName("total_count"        ) var totalCount        : Int?             = null,
  @SerializedName("incomplete_results" ) var incompleteResults : Boolean?         = null,
  @SerializedName("items"              ) var items             : ArrayList<UserItems> = arrayListOf()

)