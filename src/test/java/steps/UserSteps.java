package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import model.LoginUser;
import model.User;

import java.net.HttpURLConnection;

import static data.EndPoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class UserSteps {

    @Getter
    private static String token;

    @Setter
    @Getter
    private static boolean userCreated = false;

    @Step("Create user: send post request to " +  CREATE_USER)
    public static Response createUser(User user)
    {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(CREATE_USER);
        if (!userCreated) {
            userCreated = (response.getStatusCode() == HttpURLConnection.HTTP_OK);
            token = response
                    .path("accessToken");
        }
        return response;
    }

    @Step("Check status code and body of response")
    public static void checkCreationResponse(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("$", hasKey("user"))
                .body("user", hasKey("email"))
                .body("user", hasKey("name"))
                .body("$", hasKey("accessToken"))
                .body("$", hasKey("refreshToken"));
    }

    @Step("Check status code and body of response when create user with existing email")
    public static void checkCreationResponseWhenExistingEmail(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Step("Check status code and body of response when create user without field")
    public static void checkCreationResponseWhenWithoutField(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    //--------------------------------------------------------------------------------

    @Step("Delete user: send post request to " +  DELETE_USER)
    public static void deleteUser()
    {
        given()
                .when()
                .header("Authorization", token)
                .delete(DELETE_USER);
    }

    //---------------------------------------------------------------------------------

    @Step("Login user: send post request to " +  LOGIN_USER)
    public static Response loginUser(LoginUser lUser)
    {
        return given()
                .contentType(ContentType.JSON)
                .body(lUser)
                .post(LOGIN_USER);
    }

    @Step("Check status code and body of response")
    public static void checkLoginResponse(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("$", hasKey("accessToken"))
                .body("$", hasKey("refreshToken"))
                .body("$", hasKey("user"))
                .body("user", hasKey("email"))
                .body("user", hasKey("name"));
    }

    @Step("Check status code and body of response when login user with wrong data")
    public static void checkLoginResponseWhenWrongData(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

}
