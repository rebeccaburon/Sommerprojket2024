package app.controllers;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import app.validators.EmailValidator;
import app.validators.PasswordValidator;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class CustomerController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("createuser", ctx -> ctx.render("create_customer_page.html"));
        app.get("/login_page", ctx -> ctx.render("login_page.html"));
        app.post("createuser", ctx -> {
            createCustomer(ctx, connectionPool);
        });

        app.get("/customerinfo", ctx -> ctx.render("customer/customer-info-frontpage.html"));
    }

    private static void createCustomer(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        String name = ctx.formParam("name");
        String lastName = ctx.formParam("lastname");
        String email = ctx.formParam("email");
        String email2 = ctx.formParam("email2");
        String password = ctx.formParam("password");
        String password2 = ctx.formParam("password2");

        if (EmailValidator.isValidEmail(email)) {
            if (PasswordValidator.isValidPassword(password)) {
                if (password.equals(password2)) {
                    if (email.equals(email2)) {
                        try {
                            CustomerMapper.createUser(name, lastName, email, password, connectionPool);
                            ctx.attribute("message", "E-mail: " + email + " is Registered. Go login!.");
                            ctx.render("create_customer_page.html");
                        } catch (DatabaseException e) {
                            ctx.attribute("message", "E-mail already exists. Try again, or Log in");
                            ctx.render("login_page.html");
                        }
                    } else {
                        ctx.attribute("message", "The emails do not match. Try again.");
                        ctx.render("create_customer_page.html");
                    }
                } else {
                    ctx.attribute("message", "The passwords do not match. Try again.");
                    ctx.render("create_customer_page.html");
                }
            } else {
                ctx.attribute("message", "The password is not valid. It has to be at least 8 characters long, contain one uppercase letter, one lowercase letter, and one number. Try again.");
                ctx.render("create_customer_page.html");
            }
        } else {
            ctx.attribute("message", "The email is not valid. Try again.");
            ctx.render("create_customer_page.html");
        }
    }
}
