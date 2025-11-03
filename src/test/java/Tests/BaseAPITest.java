package Tests;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

import static data.Url.URL;

public class BaseAPITest {
    @BeforeClass
    public static void globalBaseAPISetup()
    {
        RestAssured.baseURI = URL;
    }

}
