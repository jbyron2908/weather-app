package com.jbyron.weatherapp.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ConsolidatedWeather(
        @JsonProperty("id") val id: Long,
        @JsonProperty("weather_state_name") val weatherStateName: String,
        @JsonProperty("weather_state_abbr") val weatherStateAbbr: String,
        @JsonProperty("wind_direction_compass") val windDirectionCompass: String,
        @JsonProperty("created") val created: String,
        @JsonProperty("applicable_date") val applicableDate: String,
        @JsonProperty("min_temp") val minTemp: Double,
        @JsonProperty("max_temp") val maxTemp: Double,
        @JsonProperty("the_temp") val theTemp: Double,
        @JsonProperty("wind_speed") val windSpeed: Double,
        @JsonProperty("wind_direction") val windDirection: Double,
        @JsonProperty("air_pressure") val airPressure: Double,
        @JsonProperty("humidity") val humidity: Int,
        @JsonProperty("visibility") val visibility: Double,
        @JsonProperty("predictability") val predictability: Int
)