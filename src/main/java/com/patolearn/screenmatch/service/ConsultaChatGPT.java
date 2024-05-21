package com.patolearn.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import io.github.cdimascio.dotenv.Dotenv;

public class ConsultaChatGPT {
    public static String obtenerTraduccion(String texto) {
        String apiKey = Dotenv.load().get("OPENAI_API_KEY");

        OpenAiService service = new OpenAiService(apiKey);

        CompletionRequest requisicion = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduce a español el siguiente texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        CompletionResult respuesta = service.createCompletion(requisicion);
        return respuesta.getChoices().get(0).getText();
    }
}
