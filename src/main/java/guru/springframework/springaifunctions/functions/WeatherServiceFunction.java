package guru.springframework.springaifunctions.functions;

import guru.springframework.springaifunctions.model.WeatherRequest;
import guru.springframework.springaifunctions.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

/**
 * Modified by Pierrot, 02-12-2025.
 */
@Slf4j
public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    public static final String WEATHER_URL = "https://api.api-ninjas.com/v1/weather";

    private final String apiNinjasKey;

    public WeatherServiceFunction(String apiNinjasKey) {
        this.apiNinjasKey = apiNinjasKey;
    }

    @Override
    public WeatherResponse apply(WeatherRequest weatherRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl(WEATHER_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", apiNinjasKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                }).build();

        return restClient.get().uri(uriBuilder -> {
            log.info("### Building URI for weather request: {} ###", weatherRequest);

            uriBuilder.queryParam("longitude", weatherRequest.lon());
            uriBuilder.queryParam("latitude", weatherRequest.lat());

            return uriBuilder.build();
        }).retrieve().body(WeatherResponse.class);
    }
}























