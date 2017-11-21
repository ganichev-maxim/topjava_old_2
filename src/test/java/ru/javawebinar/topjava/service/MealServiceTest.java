package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    @Autowired
    MealService service;

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        //2015-05-30T10:00:00
        assertMatch(service.getBetweenDates(LocalDate.of(2015, 05, 30),LocalDate.of(2015, 05, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        assertMatch(service.getBetweenDateTimes(LocalDateTime.of(2015, 05, 30, 1, 0, 0), LocalDateTime.of(2015, 05, 30, 14, 0 , 0), USER_ID), MEAL2, MEAL1);
    }

    @Test
    public void testGetAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = new Meal(MEAL1);
        meal.setCalories(3000);
        meal.setDescription("test");
        service.update(meal, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), meal);
    }

    @Test
    public void testCreate() throws Exception {
        Meal meal = new Meal(null, LocalDateTime.of(2017, 1, 1, 14, 0, 0), "test", 600);
        Meal created = service.create(meal, USER_ID);
        meal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), meal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

    }

    @Test(expected = NotFoundException.class)
    public void deleteNotMineMeal() throws Exception{
        service.delete(MEAL2_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotMineMeal() throws Exception{
        service.get(MEAL3_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotMineMeal() throws Exception{
        service.update(MEAL4, ADMIN_ID);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMealCreate() throws Exception{
        service.create(new Meal(MEAL4.getDateTime(), MEAL3.getDescription(), MEAL3.getCalories()), USER_ID);
    }
}