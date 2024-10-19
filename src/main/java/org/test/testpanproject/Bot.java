package org.test.testpanproject;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.test.testpanproject.botConf.BotProperties;
import org.test.testpanproject.command.AddCategoryCommand;
import org.test.testpanproject.command.DeleteCategoryCommand;
import org.test.testpanproject.command.GetCategoriesCommand;
import org.test.testpanproject.service.BotService;
import org.test.testpanproject.service.InterfaceService;

@Component
public class Bot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final InterfaceService interfaceService;

    private final CommandProcessor commandProcessor;

    public Bot(BotProperties botProperties, InterfaceService interfaceService, BotService botService) {
        super(botProperties.getBotToken());

        this.botProperties = botProperties;
        this.interfaceService = interfaceService;

        this.commandProcessor = new CommandProcessor(new AddCategoryCommand(botService), new DeleteCategoryCommand(botService),
                new GetCategoriesCommand(botService));

    }

    @Override
    public void onUpdateReceived(Update update) {

        createCommandMenu();

        long chatId;

        if (update.hasMessage()){
            chatId = update.getMessage().getChatId();

            if (update.getMessage().hasText()){
                String sendMessageText = processMessageText(update.getMessage().getText());
                sendMessage(chatId, sendMessageText);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getBotName();
    }

    private String processMessageText(String receivedMessageText){

        String sendMessageText;

        if (receivedMessageText.equals("/start")) {

            sendMessageText = "Hello user!\n" + "I'm telegram category tree bot.";
        }
        else if (receivedMessageText.equals("/help"))
            sendMessageText = interfaceService.getAllCommandsDescription();
        else if (receivedMessageText.equals("/viewTree"))
            sendMessageText = commandProcessor.getCategoriesTree();
        else if (receivedMessageText.startsWith("/addElement"))
            sendMessageText = commandProcessor.addCategory(receivedMessageText);
        else if (receivedMessageText.startsWith("/removeElement"))
            sendMessageText = commandProcessor.deleteCategory(receivedMessageText);
        else sendMessageText = "Unacceptable command.";

        return sendMessageText;
    }

    private void sendMessage(long chatId, String sendMessageText){
        try {
            execute(new SendMessage(String.valueOf(chatId), sendMessageText));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCommandMenu(){
        try {
            execute(interfaceService.getMenuCommands());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
