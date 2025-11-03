package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Order;

import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.List;

import static data.EndPoints.CREATE_ORDER;
import static org.junit.Assert.assertArrayEquals;
import static steps.UserSteps.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class OrderSteps {

    @Step("Create order without authorization: send post request to " +  CREATE_ORDER)
    public static Response createOrder(Order order)
    {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .post(CREATE_ORDER);
    }

    @Step("Check status code and body of response")
    public static void checkResponseWhenCreationWithoutAuth(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("$", hasKey("name"))
                .body("$", hasKey("order"))
                .body("order", hasKey("number"));
    }

    //------------------------------------------------------------------------------------------------

    @Step("Create order with authorization: send post request to " +  CREATE_ORDER)
    public static Response createOrderWithAuthorization(Order order)
    {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", getToken())
                .body(order)
                .post(CREATE_ORDER);
    }

    @Step("Check status code and body of response")
    public static void checkResponseWhenCreationWithAuth(Response response, String[] ingredients)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("$", hasKey("name"))
                .body("$", hasKey("order"))
                .body("order", hasKey("ingredients"));

        List<LinkedHashMap> responseIngedients = response.path("order.ingredients");
        String[] ids = new String[responseIngedients.size()];
        for (int i = 0; i < responseIngedients.size(); i++)
        {
            ids[i] = responseIngedients.get(i).get("_id").toString();
        }
        assertArrayEquals(ingredients, ids);
    }

    //----------------------------------------------------------------------------------

    @Step("Check status code and body of response when create order without ingredients")
    public static void checkResponseWhenCreationWithoutIngredients(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Check status code and body of response when create order with wrong ingredients")
    public static void checkResponseWhenCreationWithWrongIngredients(Response response)
    {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .body("success", equalTo(false))
                .body("message", equalTo("One or more ids provided are incorrect"));
    }


}
