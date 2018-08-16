package com.epam.repository.implementation;

import com.epam.entity.Category;
import com.epam.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> getAllCategories() {
        Session session = sessionFactory.getCurrentSession();
        Query<Category> query = session.createQuery("from Category", Category.class);
        return query.list();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Category category = session.get(Category.class, id);
        return Optional.ofNullable(category);
    }

    @Override
    public Category addCategory(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.save(category);
        return category;
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Category> query = session.createQuery("from Category as c"
            + " where c.name = :name", Category.class);
        query.setParameter("name", name);
        List<Category> list = query.list();
        if (list.size() == 1) {
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }
}
