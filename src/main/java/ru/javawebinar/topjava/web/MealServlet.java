package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Ganichev on 07.11.2017.
 */
public class MealServlet extends HttpServlet{

    private static final DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Logger log = getLogger(MealServlet.class);

    private MealService mealService = new MealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealService.getAllMeals(), LocalTime.of(0,0), LocalTime.of(23, 59), 2000));
        request.setAttribute("dateFormater", dateFormater);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
