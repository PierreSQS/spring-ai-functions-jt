package guru.springframework.springaifunctions.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Modified by Pierrot, 02-12-2025.
 * <p>
 * As per Ninja's Weather API, now using lon and lat for location
 * for the free pricing plan.
 * <p>
 * <a href="https://api-ninjas.com/api/weather">ninja documentation</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Weather API request")
public record WeatherRequest(@JsonProperty(required = true, value = "longitude")
                             @JsonPropertyDescription("Longitude of the city") String lon,
                             @JsonProperty(required = true, value = "latitude")
                             @JsonPropertyDescription("Latitude of the city") String lat,

                             @JsonProperty(required = false)
                             @JsonPropertyDescription("Optional State for US Cities Only. Use full name of State") String state,
                             @JsonProperty(required = false)
                             @JsonPropertyDescription("Optional Country name") String country){
}
