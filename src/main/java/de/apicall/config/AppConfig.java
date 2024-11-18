package de.apicall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import de.apicall.commands.ApiCommand;
import de.apicall.controller.ApiController;
import de.apicall.evolutions.EvolutionController;
import de.apicall.repository.PokemonRepository;
import de.apicall.repository.PokemonRepositoryCustomImpl;
import de.apicall.repository.UserPokemonRepository;
import de.apicall.repository.UserPokemonRepositoryCustomImpl;
import de.apicall.repository.UserRepository;
import de.apicall.repository.UserRepositoryCustomImpl;
import de.apicall.roles.config.RoleConfigLoader;
import de.apicall.roles.listeners.RoleCommandListener;
import de.apicall.roles.services.RoleService;
import de.apicall.services.MessageService;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ApiCommand apiCommand(RestTemplate restTemplate, MessageService messageService, ApiController apiController, EvolutionController evocontroller, RoleConfigLoader roleConfig) {
        return new ApiCommand(restTemplate, messageService, apiController, evocontroller, roleConfig);
    }
    @Bean 
    public RoleCommandListener roleCommandListener(RoleService roleService) {
    	return new RoleCommandListener(roleService); 
    }
    @Bean 
    public PokemonRepository pokemonRepository() {
        return new PokemonRepositoryCustomImpl(); 
    }
    @Bean 
    public UserRepository userRepository() {
    	return new UserRepositoryCustomImpl(); 
    }
    @Bean 
    public UserPokemonRepository userPokemonRepository() {
    	return new UserPokemonRepositoryCustomImpl(); 
    }
}
