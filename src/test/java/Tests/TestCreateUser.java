package Tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.*;
import static steps.UserSteps.*;


public class TestCreateUser extends BaseAPITest{

    @Before
    public void setup()
    {
        setUserCreated(false);
    }

    @After
    public void tearDown()
    {
        if (!isUserCreated())
            return;
        deleteUser();
    }

    @Test
    @DisplayName("Check user successful creation")
    @Description("Create user with correct data")
    public void checkUserCreation()
    {
        User user = new User(genEmail(), genPassword(), genName());
        Response response = createUser(user);
        checkCreationResponse(response);
    }

    @Test
    @DisplayName("Check response for creation of user with existing email")
    @Description("User with existing email cannot be created")
    public void checkUserWithExistingEmailCreation()
    {
        User user = new User(genEmail(), genPassword(), genName());
        createUser(user);
        Response response = createUser(user);
        checkCreationResponseWhenExistingEmail(response);
    }

    @Test
    @DisplayName("Check response for creation of user without email")
    @Description("User without email cannot be created")
    public void checkUserWithoutEmailCreation()
    {
        User user = new User("", genPassword(), genName());
        Response response = createUser(user);
        checkCreationResponseWhenWithoutField(response);
    }

    @Test
    @DisplayName("Check response for creation of user without password")
    @Description("User without password cannot be created")
    public void checkUserWithoutPasswordCreation()
    {
        User user = new User(genEmail(), "", genName());
        Response response = createUser(user);
        checkCreationResponseWhenWithoutField(response);
    }

    @Test
    @DisplayName("Check response for creation of user without name")
    @Description("User without name cannot be created")
    public void checkUserWithoutNameCreation()
    {
        User user = new User(genEmail(), genPassword(), "");
        Response response = createUser(user);
        checkCreationResponseWhenWithoutField(response);
    }
}
