package com.mahfuznow.recyclerview.model

data class Video(
    val title: String,
    val url: String,
    val thumbnail: String,
    val description: String,
    var seekPosition: Long? = null
)
