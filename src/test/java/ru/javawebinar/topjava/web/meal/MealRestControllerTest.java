package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.lang.reflect.Array;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.contentJson;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;
import static ru.javawebinar.topjava.MealTestData.*;


public class MealRestControllerTest extends AbstractControllerTest {


    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJson(MEALS.toArray(new Meal[MEALS.size()])));

    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(mealService.getAll(AuthorizedUser.id()), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);

    }

    @Test
    public void testCreateWithLocation() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2018, 2, 1, 1, 1, 1), "test", 1000);
        ResultActions actions = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isCreated());

        Meal returnedMeal = TestUtil.readFromJson(actions, Meal.class);
        meal.setId(returnedMeal.getId());

        assertMatch(returnedMeal, meal);
        assertMatch(mealService.getAll(AuthorizedUser.id()), returnedMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = new Meal(MEAL1_ID, LocalDateTime.of(2018, 2, 1, 1, 1, 1), "test", 1000);

        mockMvc.perform(put(REST_URL + "/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isOk());

        assertMatch(mealService.get(MEAL1_ID, AuthorizedUser.id()), meal);
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "/by?startDateTime=2015-05-30T09:00:00&endDateTime=2015-05-30T11:00:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJson(new Meal[] {MEAL1}));
    }
}