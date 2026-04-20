package guru.springframework.springaifunctions.functions;

import guru.springframework.springaifunctions.model.WeatherRequest;
import guru.springframework.springaifunctions.model.WeatherResponse;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

/**
 * Created by Claude Sonnet 4.6, on 20-04-2026.
 * Refactored by Claude Sonnet 4.6, on 20-04-2026.
 * <p>
 * Spring AI tool callback that fetches current weather data from the API-Ninjas
 * weather endpoint for a given longitude/latitude. The API key is supplied by
 * the caller (OpenAIServiceImpl) so this class stays free of Spring context
 * dependencies and can be used as a plain {@link java.util.function.Function}.
 */
public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    private static final String NINJA_WEATHER_URL = "https://api.api-ninjas.com/v1/weather";
    private static final String API_KEY_HEADER = "X-Api-Key";

    private final RestClient restClient;

    // API key is passed in by the caller; RestClient is built once per instance
    // to avoid overhead of building it on every function call
    public WeatherServiceFunction(String apiNinjasKey) {
        this.restClient = RestClient.builder()
                .baseUrl(NINJA_WEATHER_URL)
                .defaultHeader(API_KEY_HEADER, apiNinjasKey)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public WeatherResponse apply(WeatherRequest request) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lon", request.lon())
                        .queryParam("lat", request.lat())
                        .build())
                .retrieve()
                .body(WeatherResponse.class);
    }
}