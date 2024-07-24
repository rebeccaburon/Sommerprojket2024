package app.controllers;

import app.entities.Customer;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class LoginController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/", ctx -> ctx.render("login_page.html"));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.post("/start_page", ctx -> ctx.render("start_page.html"));
    }


    public static void login(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            Customer customer = CustomerMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser", customer);
            ctx.attribute("message", "Du er nu login");
            ctx.render("/start_page.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("login_page.html");
        }

    }

    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }
}

