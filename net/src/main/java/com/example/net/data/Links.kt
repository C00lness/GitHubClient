package com.example.net.data

import com.google.gson.annotations.SerializedName


data class Links (

  @SerializedName("self" ) var self : String? = null,
  @SerializedName("git"  ) var git  : String? = null,
  @SerializedName("html" ) var html : String? = null

)