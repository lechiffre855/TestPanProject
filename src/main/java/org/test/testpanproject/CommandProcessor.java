package org.test.testpanproject;

import org.test.testpanproject.command.AddCategoryCommand;
import org.test.testpanproject.command.DeleteCategoryCommand;
import org.test.testpanproject.command.GetCategoriesCommand;
import org.test.testpanproject.exception.CategoryAlreadyExistsExeption;
import org.test.testpanproject.exception.CategoryNotFoundException;
import org.test.testpanproject.exception.ParentCategoryNotFoundException;
import org.test.testpanproject.exception.UnacceptableCommandException;

public class CommandProcessor {

    private final AddCategoryCommand addCategoryCommand;
    private final DeleteCategoryCommand deleteCategoryCommand;
    private final GetCategoriesCommand getCategoriesCommand;

    public CommandProcessor(AddCategoryCommand addCategoryCommand, DeleteCategoryCommand deleteCategoryCommand, GetCategoriesCommand getCategoriesCommand) {
        this.addCategoryCommand = addCategoryCommand;
        this.deleteCategoryCommand = deleteCategoryCommand;
        this.getCategoriesCommand = getCategoriesCommand;
    }

    public String addCategory(String commandText){

        return addCategoryCommand.execute(commandText);
    }

    public String deleteCategory(String commandText){
        return deleteCategoryCommand.execute(commandText);
    }

    public String getCategoriesTree(){
        return getCategoriesCommand.execute("");
    }
}
