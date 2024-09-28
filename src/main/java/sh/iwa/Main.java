package sh.iwa;

import org.springframework.boot.SpringApplication;

public class Main {

    private static final String DISCORD_TOKEN = EnvConfig.get("DISCORD_TOKEN");

    public static void main(String[] args) {
        try {
            Client.start(DISCORD_TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpringApplication.run(ApiController.class, args);
    }
}
