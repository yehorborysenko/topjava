package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao mealDao;
    private static final int CALORIES_LIMIT = 2000;
    private static final String CREATE_OR_UPDATE = "createOrUpdateMeal.jsp";
    private static final String LIST = "meals.jsp";
    private static final String SERVLET_URL = "meals";

    public void init() {
        mealDao = new MealDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");

        switch (action) {
            case "createOrUpdate" :
                if (request.getParameter("id") == null) {
                    log.debug("forward to create meal");
                    request.setAttribute("action", true);
                }
                else {
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("meal", mealDao.get(id));
                    request.setAttribute("action", false);
                    log.debug("forward to update meal");
                }
                request.getRequestDispatcher(CREATE_OR_UPDATE).forward(request, response);
                break;
            case "delete" :
                int id = Integer.parseInt(request.getParameter("id"));
                mealDao.delete(id);
                log.debug("redirect to meals");
                response.sendRedirect(SERVLET_URL);
                break;
            default:
                request.setAttribute("meals", getMealToList());
                log.debug("forward to meals");
                request.getRequestDispatcher(LIST).forward(request, response);
        }
    }

    private List<MealTo> getMealToList() {
        return MealsUtil.filteredByStreams(mealDao.findAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_LIMIT);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        int id = req.getParameter("id").isEmpty() ? -1 : Integer.parseInt(req.getParameter("id"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));

        if (id == -1) {
            mealDao.create(new Meal(dateTime, description, calories));
        }
        else {
            mealDao.update(new Meal(id, dateTime, description, calories));
        }
        log.debug("redirect to meals");
        resp.sendRedirect(SERVLET_URL);
    }
}
