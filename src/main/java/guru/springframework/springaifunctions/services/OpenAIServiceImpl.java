package guru.springframework.springaifunctions.services;


import guru.springframework.springaifunctions.functions.WeatherServiceFunction;
import guru.springframework.springaifunctions.model.Answer;
import guru.springframework.springaifunctions.model.Question;
import guru.springframework.springaifunctions.model.WeatherRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Modified by Pierrot, 02-12-2025.
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {


    @Value("${API_NINJAS_KEY}")
    private String apiNinjasKey;

    private final ChatClient.Builder chatClientBuilder;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClientBuilder = chatClientBuilder;
    }

    @Override
    public Answer getAnswer(Question question) {
        // Define the tool callback for the weather service function
        ToolCallback toolCallback = FunctionToolCallback
                .builder("currentWeather", new WeatherServiceFunction(apiNinjasKey))
                .description("Get the weather in location with longitude and latitude")
                .inputType(WeatherRequest.class)
                .build();

        // Create chat options with the tool callback
        ChatOptions chatOptions = ToolCallingChatOptions.builder()
                .toolCallbacks(toolCallback)
                .build();

        // Call the chat client with the question and chat options
        // and return the answer in the JSON format
        return chatClientBuilder.build()
                .prompt()
                .user(question.question())
                .options(chatOptions)
                .call()
                .entity(Answer.class); // format the response in Answer JSON object

    }
}
