import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class XueqiuTest {

    public static Response response;

    @BeforeClass
    public static void xueqiulogin(){
        useRelaxedHTTPSValidation();
      //  RestAssured.proxy("http://192.168.31.37:8888");
        response=given()
                .log().all()
                .header("User-Agent","Xueqiu Android 10.3.1")
                .queryParam("_t","1GENYMOTION749fb879303860c8756800945bafeb27.8583398334.1523777043177.1523777071452")
                .queryParam("_s","e23897")
                .cookie("xq_a_token","ba21adebf7a9cc00488cbfee578982f607c69374")
                .cookie("u","8583398334")
                .formParam("password","6fb38a52190b64759095d48f1e8bcad7")
                .formParam("telephone","13683177341")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .formParam("areacode",86)
                .formParam("sid","1GENYMOTION749fb879303860c8756800945bafeb27")
                .formParam("client_id","JtXbaMn7eP")
                .formParam("grant_type","password")
                .when().post("https://api.xueqiu.com/provider/oauth/token")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response()
        ;

    }

    @Test
    public void testnew(){

        given()
                .cookie("xq_a_token",response.path("access_token"))
                .cookie("u",response.path("uid"))
                .queryParam( "_t","1GENYMOTION749fb879303860c8756800945bafeb27.4795294866.1523777043177.1523781296877")
                .queryParam("_s","5cf684")
                .header("User-Agent","Xueqiu Android 10.3.1")
                .when().get("https://api.xueqiu.com/cubes/allow_create.json")
                .then().log().all()
                .statusCode(200)
        ;
    }


    @Test
    public void testSearch(){

        given()
                // .log().all()
                .queryParam("code","科技")
                .header("Cookie","device_id=73b60b60dc6142c2041cd1f50d90531b; xq_a_token=229a3a53d49b5d0078125899e528279b0e54b5fe; xq_a_token.sig=oI-FfEMvVYbAuj7Ho7Z9mPjGjjI; xq_r_token=8a43eb9046efe1c0a8437476082dc9aac6db2626; xq_r_token.sig=Efl_JMfn071_BmxcpNvmjMmUP40; Hm_lvt_1db88642e346389874251b5a1eded6e3=1523697487; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1523697487; u=121523697487501; __utma=1.1083652591.1520745998.1520745998.1523697488.2; __utmc=1; __utmz=1.1523697488.2.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmt=1; __utmb=1.1.10.1523697488")
                .when()
                .get("http://xueqiu.com/stock/search.json")
                .then()
                .log().all()
                .body("stocks.find { it.code == 'SZ002610' }.name",equalTo("爱康科技"))
                .body("stocks.findAll { it.code == 'SZ002610' }.name",hasItems("爱康科技"))
                .body("stocks[1].code",equalTo("SH600391"))
                //   .body("stocks[9].find{ it.code == 'SH600562' }.name",equalTo("国睿科技"))
                .body("stocks.size()",equalTo(10))
        ;
    }
}
