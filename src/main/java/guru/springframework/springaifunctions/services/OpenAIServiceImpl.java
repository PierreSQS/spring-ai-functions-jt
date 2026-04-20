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
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;
    private final String apiNinjasKey;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder,
                             @Value("${sfg.aiapp.apiNinjasKey}") String apiNinjasKey) {
        this.chatClient = chatClientBuilder.build();
        this.apiNinjasKey = apiNinjasKey;
    }

    @Override
    public Answer getAnswer(Question question) {
        var weatherFunction = new WeatherServiceFunction(apiNinjasKey);

        var weatherToolCallback = FunctionToolCallback.builder("weatherFunction", weatherFunction)
                .description("Get current weather for a given longitude and latitude")
                .inputType(WeatherRequest.class)
                .build();

        String response = chatClient.prompt()
                .user(question.question())
                .tools(weatherToolCallback)
                .call()
                .content();

        return new Answer(response);
    }
}