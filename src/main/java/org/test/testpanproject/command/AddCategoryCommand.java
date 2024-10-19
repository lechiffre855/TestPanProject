package org.test.testpanproject.command;

import org.test.testpanproject.exception.CategoryAlreadyExistsExeption;
import org.test.testpanproject.exception.ChildCategoryAlreadyExistsExeption;
import org.test.testpanproject.exception.ParentCategoryNotFoundException;
import org.test.testpanproject.exception.UnacceptableCommandException;
import org.test.testpanproject.service.BotService;

import java.util.regex.PatternSyntaxException;

// Класс-команда по добавлению категории
public class AddCategoryCommand implements Command{

    private final BotService botService;

    public AddCategoryCommand(BotService botService) {
        this.botService = botService;
    }

    // Логика по дообработке команды и работе с классом-сервисом
    @Override
    public String execute(String commandText) {
        String rightFormatMessage = "You need to write in this format: \n'/addElement <parent category> <child category>'";
        if (commandText.equals("/addElement"))
            return rightFormatMessage;
        try {
            String[] textElements = commandText.split(" ");

            if (textElements.length == 2) {
                String rootCategoryName = textElements[1];
                botService.addRootCategory(rootCategoryName);
            } else if (textElements.length == 3) {
                String parentCategoryName = textElements[1];
                String childCategoryName = textElements[2];
                botService.addChildCategoryToParentCategory(parentCategoryName, childCategoryName);
            } else return rightFormatMessage;

            return "The category was successfully added.";

        } catch (CategoryAlreadyExistsExeption e){
            return "The given category already exists.";
        } catch (ChildCategoryAlreadyExistsExeption e){
            return "The given child category already exists.";
        } catch (ParentCategoryNotFoundException e){
            return "The parent category was not found.";
        } catch (PatternSyntaxException e){
            return rightFormatMessage;
        }
    }
}
