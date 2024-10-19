package org.test.testpanproject.botConf;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.test.testpanproject.Bot;

// класс с реализацией инициализации бота в телеграм-апи
@Component
public class BotInitializer {
    private final Bot bot;

    public BotInitializer(Bot bot) {
        this.bot = bot;
    }

    // Инициализация бота после сборки контекста спринга
    @EventListener({ContextRefreshedEvent.class})
    public void toRegisterBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class); /* Передаем класс DefaultBotSession.class
        для работы с ботом в режиме Long Polling */
        botsApi.registerBot(bot); // регистрируем класс бота в телеграм-апи
    }
}
