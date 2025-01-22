package com.example.gitclient.data

import com.google.gson.annotations.SerializedName


data class GitRepositories (

  @SerializedName("total_count"        ) var totalCount        : Int?             = null,
  @SerializedName("incomplete_results" ) var incompleteResults : Boolean?         = null,
  @SerializedName("items"              ) var items             : ArrayList<Items> = arrayListOf()

)