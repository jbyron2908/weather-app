package com.jbyron.weatherapp.model

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherData(
        @JsonProperty("consolidated_weather") val consolidatedWeather: List<ConsolidatedWeather>,
        @JsonProperty("time") val time: String,
        @JsonProperty("sun_rise") val sunRise: String,
        @JsonProperty("sun_set") val sunSet: String,
        @JsonProperty("timezone_name") val timezoneName: String,
        @JsonProperty("parent") val parent: Parent,
        @JsonProperty("sources") val sources: List<Source>,
        @JsonProperty("title") val title: String,
        @JsonProperty("location_type") val locationType: String,
        @JsonProperty("woeid") val woeid: Int,
        @JsonProperty("latt_long") val lattLong: String,
        @JsonProperty("timezone") val timezone: String
)