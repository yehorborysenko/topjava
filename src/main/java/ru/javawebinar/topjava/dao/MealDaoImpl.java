package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MealDaoImpl implements MealDao {
    private static final Logger log = LoggerFactory.getLogger(MealDaoImpl.class);
    private final AtomicInteger lastId = new AtomicInteger();
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MealDaoImpl() {
        MealsUtil.getMeals().forEach(meal -> meals.put(meal.getId(), meal));
    }

    @Override
    public void create(Meal meal) {
        log.debug("create {}", meal);
        int id = lastId.incrementAndGet();
        meals.put(id, new Meal(id, meal.getDateTime(), meal.getDescription(),
                meal.getCalories()));
    }

    @Override
    public void update(Meal meal) {
        log.debug("meal {}", meal);
        meals.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        log.debug("delete id {}", id);
        meals.remove(id);
    }

    @Override
    public Meal get(int id) {
        log.debug("get id {}", id);
        return meals.get(id);
    }

    @Override
    public List<Meal> findAll() {
        log.debug("findAll");
        return new ArrayList<>(meals.values());
    }
}
