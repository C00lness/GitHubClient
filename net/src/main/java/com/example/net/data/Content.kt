package com.example.net.data

import com.google.gson.annotations.SerializedName


data class Content (

  @SerializedName("name"         ) var name        : String? = null,
  @SerializedName("path"         ) var path        : String? = null,
  @SerializedName("sha"          ) var sha         : String? = null,
  @SerializedName("size"         ) var size        : Int?    = null,
  @SerializedName("url"          ) var url         : String? = null,
  @SerializedName("html_url"     ) var htmlUrl     : String? = null,
  @SerializedName("git_url"      ) var gitUrl      : String? = null,
  @SerializedName("download_url" ) var downloadUrl : String? = null,
  @SerializedName("type"         ) var type        : String? = null,
  @SerializedName("_links"       ) var Links       : Links?  = Links()

)