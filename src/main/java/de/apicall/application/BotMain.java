package de.apicall.application;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageActivity.Application;
import net.dv8tion.jda.api.requests.GatewayIntent;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import de.apicall.data.DataListener;
import de.apicall.handlers.CommandHandler;
import de.apicall.handlers.EventHandler;
import de.apicall.utils.CommandRegistry;
import de.apicall.utils.H2DataInitializer;
import de.apicall.utils.SpringContextHelper;

@SpringBootApplication(scanBasePackages = "de.apicall")
//@EntityScan(basePackages = "de.apicall.entity")
public class BotMain implements CommandLineRunner {
	
    private static final String DISCORD_TOKEN_ENV = "DISCORD_TOKEN";

    @Autowired 
    private CommandRegistry commandRegistry; 

    @Autowired
    private H2DataInitializer dataInitializer;  
    
    @Autowired 
    private ApplicationContext applicationContext; 
    
    public static void main(String[] args) {
    	SpringApplication.run(BotMain.class, args); 
    }

    

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	     String token = getDiscordToken(); 
	     

	        JDA jda = JDABuilder.createDefault(token)
	                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
	                .setActivity(Activity.playing("API-READY"))
	                .build();
	      //jda.addEventListener(applicationContext.getBean(DataListener.class));	      	 
	      new CommandHandler(jda, commandRegistry); 
	      new EventHandler(jda);
	      dataInitializer.onApplicationReady(); 
	      System.out.println("Bot und Datenbank initialisiert.");
	}
	
	   private String getDiscordToken() {
	        String token = System.getenv(DISCORD_TOKEN_ENV);
	        if (token == null || token.isEmpty()) {
	            throw new IllegalArgumentException("Der Discord-Bot-Token ist nicht gesetzt. Bitte setzen Sie die Umgebungsvariable '" + DISCORD_TOKEN_ENV + "'.");
	        }
	        return token;
	    }
}
