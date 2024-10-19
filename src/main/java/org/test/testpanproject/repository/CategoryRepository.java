package org.test.testpanproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.test.testpanproject.entity.Category;

import java.util.List;
import java.util.Optional;

// Интерфейс по работе с БД с помощью паттерна спринга "репозиторий"
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    List<Category> findAllByParentCategoryIsNull();
}
