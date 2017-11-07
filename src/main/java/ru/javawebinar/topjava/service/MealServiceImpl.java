package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealStaticDAO;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Ganichev on 07.11.2017.
 */
public class MealServiceImpl implements MealService {

    MealDAO mealDAO = new MealStaticDAO();

    @Override
    public List<Meal> getAllMeals() {
        return mealDAO.getAllMeals();
    }
}
