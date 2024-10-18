package org.test.testpanproject.botConf;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.test.testpanproject.Bot;

@Component
public class BotInitializer {
    private final Bot bot;

    public BotInitializer(Bot bot) {
        this.bot = bot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void toRegisterBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
    }
}
