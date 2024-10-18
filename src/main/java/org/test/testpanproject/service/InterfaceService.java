package org.test.testpanproject.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterfaceService {
    public SetMyCommands getMenuCommands(){
        List<BotCommand> listCommands = new ArrayList<>();
        listCommands.add(new BotCommand("/start", "Get starting bot"));
//        listCommands.add(new BotCommand("/viewTree", "Get category tree"));
//        listCommands.add(new BotCommand("/addElement", "Add new category"));
//        listCommands.add(new BotCommand("/removeElement", "Remove the category"));
        listCommands.add(new BotCommand("/help", "List of commands"));

        return new SetMyCommands(listCommands, new BotCommandScopeDefault(), null);
    }
    public String getCommandsDescription(){
        ClassPathResource classPathResource = new ClassPathResource("text-files/help_text.txt");
        try(InputStream inputStream = classPathResource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}