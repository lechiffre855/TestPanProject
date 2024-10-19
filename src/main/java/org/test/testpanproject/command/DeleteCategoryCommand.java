package org.test.testpanproject.command;

import org.test.testpanproject.exception.CategoryNotFoundException;
import org.test.testpanproject.service.BotService;

import java.util.regex.PatternSyntaxException;

// Класс-команда по удаления категории
public class DeleteCategoryCommand implements Command{

    private final BotService botService;

    public DeleteCategoryCommand(BotService botService) {
        this.botService = botService;
    }

    // Логика по дообработке команды и работе с классом-сервисом
    @Override
    public String execute(String commandText) {
        String rightFormatMessage = "You need to write in this format: \n'/removeElement <category>'";

        if (commandText.equals("/removeElement")){
            return rightFormatMessage;
        }
        try {
            String[] textElements = commandText.split(" ");
            if (textElements.length == 2) {
                String categoryName = textElements[1];
                botService.deleteCategory(categoryName);
                return "The category was successfully deleted with all its subcategories.";
            } else return rightFormatMessage;
        } catch (CategoryNotFoundException e){
            return "The given category was not found.";
        } catch (PatternSyntaxException e){
            return rightFormatMessage;
        }
    }
}
