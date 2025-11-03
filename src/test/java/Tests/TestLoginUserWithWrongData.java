package Tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.LoginUser;
import model.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static data.TestData.*;
import static steps.UserSteps.*;

@RunWith(Parameterized.class)
public class TestLoginUserWithWrongData extends BaseAPITest{
    private String description;
    private String email;
    private String password;

    private static String cEmail = genEmail();
    private static String cPassword = genPassword();
    private static String wEmail = genEmail();
    private static String wPassword = genPassword();

    public TestLoginUserWithWrongData(String description, String email, String password) {
        this.description = description;
        this.email = email;
        this.password = password;
    }

    @BeforeClass
    public static void globalSetup()
    {
        User user = new User(cEmail, cPassword, genName());
        createUser(user);
    }

    @AfterClass
    public static void globalTearDown()
    {
        deleteUser();
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getData()
    {
        return new Object[][] {
                {"wrong email", wEmail, cPassword},
                {"wrong password", cEmail, wPassword},
                {"wrong email and password", wEmail, wPassword},
                {"empty email", "", cPassword},
                {"empty password", cEmail, ""},
                {"empty email and password", "", ""}
        };
    }

    @Test
    @DisplayName("Check response for login user with wrong data")
    @Description("User with wrong data cannot login")
    public void checkLoginUserWhenWrongData()
    {
        LoginUser lUser = new LoginUser(email, password);
        Response response = loginUser(lUser);
        checkLoginResponseWhenWrongData(response);
    }

}
