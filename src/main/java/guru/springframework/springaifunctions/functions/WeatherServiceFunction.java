package guru.springframework.springaifunctions.functions;

import guru.springframework.springaifunctions.model.WeatherRequest;
import guru.springframework.springaifunctions.model.WeatherResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

/**
 * Created by Claude Sonnet 4.6, 2026-04-20.
 * <p>
 * Calls the API-Ninjas Weather endpoint with longitude/latitude
 * and maps the JSON response to a {@link WeatherResponse}.
 */
// @Component lets Spring manage this bean so @Value injection works
@Component
public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    private static final String NINJA_WEATHER_URL = "https://api.api-ninjas.com/v1/weather";
    private static final String API_KEY_HEADER = "X-Api-Key";

    // Injected from application properties (ninja.api.key)
    @Value("${ninja.api.key}")
    private String apiNinjaKey;

    private RestClient restClient;

    // @PostConstruct ensures the RestClient is built after @Value has been injected
    @PostConstruct
    void initRestClient() {
        restClient = RestClient.builder()
                .baseUrl(NINJA_WEATHER_URL)
                .defaultHeader(API_KEY_HEADER, apiNinjaKey)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public WeatherResponse apply(WeatherRequest request) {
        // lon and lat are mandatory — reject early before hitting the API
        if (request.lon() == null) {
            throw new IllegalArgumentException("lon (longitude) is required and must not be null");
        }
        if (request.lat() == null) {
            throw new IllegalArgumentException("lat (latitude) is required and must not be null");
        }

        // Pass lon/lat as query params and deserialize the JSON response
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lon", request.lon())
                        .queryParam("lat", request.lat())
                        .build())
                .retrieve()
                .body(WeatherResponse.class);
    }
}