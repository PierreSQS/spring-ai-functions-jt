package guru.springframework.springaifunctions.services;


import guru.springframework.springaifunctions.model.Answer;
import guru.springframework.springaifunctions.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Modified by Pierrot, 02-12-2025.
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Answer getAnswer(Question question) {
       return new Answer("to-do implement me!");
    }
}
