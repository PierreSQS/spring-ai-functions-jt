package guru.springframework.springaifunctions.services;


import guru.springframework.springaifunctions.functions.WeatherServiceFunction;
import guru.springframework.springaifunctions.model.Answer;
import guru.springframework.springaifunctions.model.Question;
import guru.springframework.springaifunctions.model.WeatherRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by jt, Spring Framework Guru.
 * Refactored by Claude Sonnet 4.6, on 20-04-2026.
 * <p>
 * OpenAI service implementation that uses Spring AI's {@link ChatClient} to answer
 * questions. {@link WeatherServiceFunction} is registered as a tool callback so the
 * model can invoke it to fetch live weather data when needed.
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    // Kept here so WeatherServiceFunction needs no Spring annotations
    // since it's also needed there
    private final String apiNinjasKey;

    // initializes the chatClient through the ChatClent.Builder
    // and injects the API key from application properties
    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder,
                             @Value("${sfg.aiapp.apiNinjasKey}") String apiNinjasKey) {
        this.chatClient = chatClientBuilder.build();
        this.apiNinjasKey = apiNinjasKey;
    }

    @Override
    public Answer getAnswer(Question question) {
        // Pass the key at construction time
        var weatherFunction = new WeatherServiceFunction(apiNinjasKey);

        // Register the function as a named tool callback; the model decides when to call it
        var weatherToolCallback = FunctionToolCallback.builder("weatherFunction", weatherFunction)
                .description("Get current weather for a given longitude and latitude")
                .inputType(WeatherRequest.class)  // tells Spring AI how to deserialize the model's JSON arguments
                .build();

        // tools() makes the callback available to the model for this single request
        String response = chatClient.prompt()
                .user(question.question())
                .tools(weatherToolCallback)
                .call()
                .content();

        return new Answer(response);
    }
}