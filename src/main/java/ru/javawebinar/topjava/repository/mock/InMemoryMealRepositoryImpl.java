package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal->save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals == null){
            userMeals = new HashMap<>();
            repository.put(userId, userMeals);
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        else if(!userMeals.containsKey(meal.getId())){
            return null;
        }
        userMeals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal deletedMeal = null;
        if (repository.containsKey(userId)){
            Map<Integer, Meal> userMeals = repository.get(userId);
            deletedMeal = userMeals.remove(id);
            if (userMeals.isEmpty()){
                repository.remove(userId);
            }
        }
        return deletedMeal != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.containsKey(userId)? repository.get(userId).get(id): null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        if (repository.containsKey(userId)){
            return repository.get(userId).values()
                    .stream()
                    .sorted((meal1, meal2)-> meal2.getDateTime().compareTo(meal1.getDateTime()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

