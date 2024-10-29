package de.apicall.commands;

import org.springframework.web.client.RestTemplate;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ApiCommand extends ListenerAdapter {

	private final RestTemplate restTemplate;

    public ApiCommand(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equals("!fetchdata")) {
            String response = restTemplate.getForObject("http://localhost:8080/api/data", String.class);
            event.getChannel().sendMessage("API Response: " + response).queue();
        }
    }
}
