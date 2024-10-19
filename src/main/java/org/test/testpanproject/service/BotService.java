package org.test.testpanproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.testpanproject.entity.Category;
import org.test.testpanproject.exception.*;
import org.test.testpanproject.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

// Класс сервис по работе с сущностями и БД
@Service
public class BotService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public BotService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void addRootCategory(String rootCategoryName){

            Category newRootCategory = new Category(rootCategoryName);

            categoryRepository.findByName(rootCategoryName).ifPresent(x -> {
                throw new CategoryAlreadyExistsExeption();
            });

            categoryRepository.save(newRootCategory);
    }

    @Transactional
    public void addChildCategoryToParentCategory(String parentCategoryName, String childCategoryName) {

        categoryRepository.findByName(childCategoryName).ifPresent(x -> {
            throw new ChildCategoryAlreadyExistsExeption();
        });

        Optional<Category> parentCategoryOptional = categoryRepository.findByName(parentCategoryName);
        if (parentCategoryOptional.isPresent()) {
            Category parentCategory = parentCategoryOptional.get();

            Category childCategory = new Category(childCategoryName);
            childCategory.setParentCategory(parentCategory);

            List<Category> childCategoryList = parentCategory.getCategoryList();
            childCategoryList.add(childCategory);
            parentCategory.setCategoryList(childCategoryList);

            categoryRepository.save(childCategory);
            categoryRepository.save(parentCategory);

        } else throw new ParentCategoryNotFoundException();

    }

    @Transactional
    public void deleteCategory(String categoryName){

        Optional<Category> categoryOptional = categoryRepository.findByName(categoryName);

        if (categoryOptional.isPresent()){

            Category category = categoryOptional.get();
            Category parentCategory = category.getParentCategory();

            if (parentCategory!=null){
                parentCategory.getCategoryList().remove(category);
                categoryRepository.save(parentCategory);
            }
            categoryRepository.delete(category);

        } else throw new CategoryNotFoundException();
    }

    @Transactional
    public String getCategories(){

        List<Category> categoryList = categoryRepository.findAllByParentCategoryIsNull();
        if (categoryList.isEmpty())
            throw new CategoriesListEmptyException();
        return printCategoryTree(categoryList);
    }

    private String printCategoryTree(List<Category> categoryList){

        StringBuilder sb = new StringBuilder();
        for (Category category : categoryList) {
            buildTreeString(sb, category, "");
        }
        return sb.toString();
    }

    private void buildTreeString(StringBuilder sb, Category category, String prefix){
        sb.append(prefix).append(category.getName()).append("\n");
        String newPrefix = (prefix.isEmpty() ? "--" : "  ") + prefix;
        for (Category child : category.getCategoryList()) {
            buildTreeString(sb, child, newPrefix);
        }
    }
}
