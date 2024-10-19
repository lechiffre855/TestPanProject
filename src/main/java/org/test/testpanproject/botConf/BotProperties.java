package org.test.testpanproject.botConf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

// Просто класс со свойствами
@Configuration
public class BotProperties {

    private final String botName;
    private final String botToken;

    // Берем свойства из classpath
    public BotProperties(@Value("${bot.name}") String botName, @Value("${bot.token}") String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    public String getBotName() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }
}
