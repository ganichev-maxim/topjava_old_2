package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

/**
 * Created by Ganichev on 12/28/2017.
 */
@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    private MealRestController mealController;

    @GetMapping("")
    public String getAll(HttpServletRequest request){
        request.setAttribute("meals", mealController.getAll());
        return "meals";
    }

    @GetMapping("delete")
    public String delete(HttpServletRequest request){
        int id = getId(request);
        mealController.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("create")
    public String getCreateForm(HttpServletRequest request){
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("update")
    public String getUpdateForm(HttpServletRequest request){
        final Meal meal = mealController.get(getId(request));
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("update")
    public String createOrUpdate(HttpServletRequest request){
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            mealController.create(meal);
        } else {
            mealController.update(meal, getId(request));
        }
        return "redirect:/meals";
    }

    @PostMapping("filter")
    public String filter(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
        //return "redirect:/meals";
        //request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
