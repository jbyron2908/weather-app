package com.jbyron.weatherapp.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Source(
        @JsonProperty("title") val title: String,
        @JsonProperty("slug") val slug: String,
        @JsonProperty("url") val url: String,
        @JsonProperty("crawl_rate") val crawlRate: Int
)