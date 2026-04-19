package guru.springframework.springaifunctions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Created by Pierrot, 2026-04-20.
 * <p>
 * As per Ninja's Weather API, now using lon and lat for location
 * for the free pricing plan.
 * <p>
 * <a href="https://api-ninjas.com/api/weather">ninja documentation</a>
 */
public record WeatherRequest(
        @JsonProperty("lon")
        @JsonPropertyDescription("Longitude of the city")
        double lon,

        @JsonProperty("lat")
        @JsonPropertyDescription("Latitude of the city")
        double lat
) {
}