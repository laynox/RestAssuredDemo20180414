import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class DemoTest {

    @Test
    public void testXML(){
        when().get("http://192.168.31.99:8000/testerhome.xml")
                .then()
                .body("shopping.category.item[2].name", equalTo("Paper"))
                .body("shopping.category.item.size()", equalTo(5))
                .body("shopping.category.findAll { it.@type == 'supplies' }.size()",equalTo(1))
                .body("shopping.category.item.findAll { it.price == 15 }.name",equalTo("Pens"))
                .body("**.findAll { it.price == 200 }.name",equalTo("Kathryn's Birthday"))
        ;

    }

    @Test
    public void testhomeSearch(){
        useRelaxedHTTPSValidation();
        given().queryParam("q","test").when().get("https://testerhome.com/search").then().log().all().statusCode(200);
    }
}
