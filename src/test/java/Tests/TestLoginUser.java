package Tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.LoginUser;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import steps.UserSteps;

import static data.TestData.*;
import static steps.UserSteps.*;

public class TestLoginUser extends BaseAPITest {

    private static User user;

    @BeforeClass
    public static void globalSetup()
    {
        user = new User(genEmail(), genPassword(), genName());
        createUser(user);
    }

    @AfterClass
    public static void globalTearDown()
    {
        deleteUser();
    }

    @Test
    @DisplayName("Check user successful login")
    @Description("Login user with correct data")
    public void checkLoginUser()
    {
        LoginUser lUser = new LoginUser(user.getEmail(), user.getPassword());
        Response response = UserSteps.loginUser(lUser);
        checkLoginResponse(response);
    }

}
