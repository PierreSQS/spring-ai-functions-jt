package guru.springframework.springaifunctions.functions;

import guru.springframework.springaifunctions.model.WeatherRequest;
import guru.springframework.springaifunctions.model.WeatherResponse;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

/**
 * Created by jt, Spring Framework Guru.
 */
public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    private static final String NINJA_WEATHER_URL = "https://api.api-ninjas.com/v1/weather";
    private static final String API_KEY_HEADER = "X-Api-Key";

    private final RestClient restClient;

    public WeatherServiceFunction(String apiNinjasKey) {
        this.restClient = RestClient.builder()
                .baseUrl(NINJA_WEATHER_URL)
                .defaultHeader(API_KEY_HEADER, apiNinjasKey)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public WeatherResponse apply(WeatherRequest request) {
        if (request.lon() == null) {
            throw new IllegalArgumentException("lon (longitude) is required and must not be null");
        }
        if (request.lat() == null) {
            throw new IllegalArgumentException("lat (latitude) is required and must not be null");
        }

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lon", request.lon())
                        .queryParam("lat", request.lat())
                        .build())
                .retrieve()
                .body(WeatherResponse.class);
    }
}