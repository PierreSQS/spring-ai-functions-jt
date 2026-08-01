package guru.springframework.springaifunctions.services;


import guru.springframework.springaifunctions.functions.WeatherServiceFunction;
import guru.springframework.springaifunctions.model.Answer;
import guru.springframework.springaifunctions.model.Question;
import guru.springframework.springaifunctions.model.WeatherRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Modified by Pierrot, on 20-04-2026.
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
    @Value("${sfg.aiapp.apiNinjasKey}")
    private String apiNinjasKey;

    // initializes the chatClient through the ChatClent.Builder
    // and injects the API key from application properties
    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Answer getAnswer(Question question) {
        // Pass the key at construction time
        var weatherFunction = new WeatherServiceFunction(apiNinjasKey);

        // Register the function as a named tool callback; the model decides when to call it
        var weatherToolCallback = FunctionToolCallback.builder("weatherFunction", weatherFunction)
                .description("Get current weather in location with a given longitude and latitude")
                .inputType(WeatherRequest.class)  // tells Spring AI how to deserialize the model's JSON arguments
                .build();

        // toolCallbacks() makes the callback available to the model for this single request
        return chatClient.prompt()
                .advisors(List.of(new SimpleLoggerAdvisor())) // logs the conversation to the console
                .user(question.question())
                .toolCallbacks(weatherToolCallback)
                .call()
                .entity(Answer.class); // deserialize the model's final response into our Answer class

    }
}