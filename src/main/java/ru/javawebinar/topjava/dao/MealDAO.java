package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Ganichev on 07.11.2017.
 */
public interface MealDAO {
    List<Meal> getAllMeals();
}
