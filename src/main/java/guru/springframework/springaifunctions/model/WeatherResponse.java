package guru.springframework.springaifunctions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record WeatherResponse(
        @JsonProperty("temp")
        @JsonPropertyDescription("Current temperature in Celsius")
        int temp,

        @JsonProperty("feels_like")
        @JsonPropertyDescription("Feels-like temperature in Celsius")
        int feelsLike,

        @JsonProperty("humidity")
        @JsonPropertyDescription("Relative humidity in percent")
        int humidity,

        @JsonProperty("min_temp")
        @JsonPropertyDescription("Minimum temperature in Celsius")
        int minTemp,

        @JsonProperty("max_temp")
        @JsonPropertyDescription("Maximum temperature in Celsius")
        int maxTemp,

        @JsonProperty("wind_speed")
        @JsonPropertyDescription("Wind speed in metres per second")
        double windSpeed,

        @JsonProperty("wind_degrees")
        @JsonPropertyDescription("Wind direction in degrees (0-360)")
        int windDegrees,

        @JsonProperty("cloud_pct")
        @JsonPropertyDescription("Cloud coverage percentage")
        int cloudPct,

        @JsonProperty("sunrise")
        @JsonPropertyDescription("Sunrise time as Unix timestamp")
        long sunrise,

        @JsonProperty("sunset")
        @JsonPropertyDescription("Sunset time as Unix timestamp")
        long sunset
) {
}