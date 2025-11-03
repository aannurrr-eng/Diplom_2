package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static data.TestData.*;
import static steps.OrderSteps.*;
import static steps.OrderSteps.checkResponseWhenCreationWithWrongIngredients;
import static steps.UserSteps.createUser;
import static steps.UserSteps.deleteUser;

public class TestCreateOrder extends BaseAPITest{

    private String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"};

    @BeforeClass
    public static void globalSetup()
    {
        User user = new User(genEmail(), genPassword(), genName());
        createUser(user);
    }

    @AfterClass
    public static void globalTearDown()
    {
        deleteUser();
    }

    @Test
    @DisplayName("Check order without authorization successful creation")
    @Description("Create order without authorization with correct data")
    public void checkCreateOrderWithoutAuth()
    {
        Order order = new Order(ingredients);
        Response response = createOrder(order);
        checkResponseWhenCreationWithoutAuth(response);
    }

    @Test
    @DisplayName("Check response for creation of order without authorization without ingredients")
    @Description("Order without ingredients cannot be created")
    public void checkCreateOrderWithoutAuthWithoutIngredients()
    {
        Order order = new Order(new String[]{});
        Response response = createOrder(order);
        checkResponseWhenCreationWithoutIngredients(response);
    }

    @Test
    @DisplayName("Check response for creation of order without authorization with wrong ingredients")
    @Description("Order with wrong ingredients cannot be created")
    public void checkCreateOrderWithoutAuthWithWrongIngredients()
    {
        Order order = new Order(new String[]{genHash(), genHash()});
        Response response = createOrder(order);
        checkResponseWhenCreationWithWrongIngredients(response);
    }

    @Test
    @DisplayName("Check order with authorization successful creation")
    @Description("Create order with authorization with correct data")
    public void checkCreateOrderWithAuth()
    {
        Order order = new Order(ingredients);
        Response response = createOrderWithAuthorization(order);
        checkResponseWhenCreationWithAuth(response, ingredients);
    }

    @Test
    @DisplayName("Check response for creation of order with authorization without ingredients")
    @Description("Order without ingredients cannot be created")
    public void checkCreateOrderWithAuthWithoutIngredients()
    {
        Order order = new Order(new String[]{});
        Response response = createOrderWithAuthorization(order);
        checkResponseWhenCreationWithoutIngredients(response);
    }

    @Test
    @DisplayName("Check response for creation of order with authorization with wrong ingredients")
    @Description("Order with wrong ingredients cannot be created")
    public void checkCreateOrderWithAuthWithWrongIngredients()
    {
        Order order = new Order(new String[]{genHash(), genHash()});
        Response response = createOrderWithAuthorization(order);
        checkResponseWhenCreationWithWrongIngredients(response);
    }
}
