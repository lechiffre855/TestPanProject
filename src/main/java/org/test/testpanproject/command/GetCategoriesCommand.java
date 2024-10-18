package org.test.testpanproject.command;

import org.test.testpanproject.exception.CategoriesListEmptyException;
import org.test.testpanproject.service.BotService;

public class GetCategoriesCommand implements Command{

    private final BotService botService;

    public GetCategoriesCommand(BotService botService) {
        this.botService = botService;
    }

    @Override
    public String execute(String commandText) {
        try {
            return botService.getCategories();
        } catch (CategoriesListEmptyException e){
            return "There is not any category yet.";
        }
    }
}
