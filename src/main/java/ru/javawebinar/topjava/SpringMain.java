package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "auserName", "aemail", "apassword", Role.ROLE_ADMIN));
            adminUserController.getAll().forEach(System.out::println);
            System.out.println(adminUserController.getByMail("aemail"));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(new Meal(LocalDateTime.of(2115, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            mealRestController.getAll().forEach(System.out::println);
            System.out.println(mealRestController.get(4));
            mealRestController.update(new Meal(LocalDateTime.of(2017, Month.MAY, 31, 10, 0), "Завтрак", 1000), 4);
            System.out.println(mealRestController.get(4));
            mealRestController.delete(4);
            System.out.println(mealRestController.get(4));
        }
    }
}
