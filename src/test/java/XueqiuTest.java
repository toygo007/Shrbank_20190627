import java.lang.String;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class XueqiuTest {

    public static String code;
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;

    public static void loginXueqiu(){
        //正确的密码->.formParam("password","1897a69ef451f0991bb85c6e7c35aa31")
        code = given()
                .header("User-agent","Xueqiu iPhone 11.19.1")
                .header("Host","api.xueqiu.com")
                .queryParam("_","1561100116992")
                .queryParam("x","0.81")
                .queryParam("_s","a7f7bb")
                .queryParam("_t","91E20F96-CBC1-465E-983A-BB621539A3EF.2335155538" +
                        ".1561099721263.1561099721357")
                .cookie("u","2335155538")
                .cookie("xq_a_token","161bb730e56d94dfe7803699bc63dabe23eae8e6")
                .formParam("areacode","86")
                .formParam("grant_type","password")
                .formParam("password","e10adc3949ba59abbe56e057f20f883e")
                .formParam("telephone","13917471023")
                .formParam("client_id","WiCimxpj5H")
                .formParam("client_secret","TM69Da3uPkFzIdxpTEm6hp")
                .formParam("captcha","")
                .formParam("sid","91E20F96-CBC1-465E-983A-BB621539A3EF")
                .formParam("device_uuid","91E20F96-CBC1-465E-983A-BB621539A3EF")
                .when()
                .post("https://101.201.175.228/provider/oauth/token")
                .then()
                .log().all()
                .statusCode(400)
                .body("error_code",equalTo("20082"))
                .extract().path("error_code");

        System.out.println(code);

    }

    @BeforeClass
    public static void login(){
        useRelaxedHTTPSValidation();
        //RestAssured.proxy("127.0.0.1",8887);

        //RestAssured.baseURI ="https://api.xueqiu.com";
        requestSpecification = new RequestSpecBuilder().build();
        requestSpecification.port(80);
        requestSpecification.cookie("shrbank","uat_team");
        requestSpecification.header("User-agent","Xueqiu iPhone 11.19.1");

        responseSpecification = new ResponseSpecBuilder().build();
        responseSpecification.statusCode(200);
        responseSpecification.body("code",equalTo(1));
        //loginXueqiu();

    }

    @Test
    public void testSearch(){
        useRelaxedHTTPSValidation();
        //  .proxy("127.0.0.1",8887)
        //header内的信息是有有效期的，过了一段时间就失效了
        //given()开头表示输入数据
        given()
                //query请求
                .queryParam("code","sogo")
                //头信息
                .header("Cookie","device_id=834e4c71258a3c6478ada30fdf52d7b7;" +
                        " _ga=GA1.2.58317738.1540273939; " +
                        "__utmz=1.1540274470.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); " +
                        "s=du12j4dc65; bid=086191650c4d513282dc7cef67488dc0_jnpo5pr6; remember=1; " +
                        "remember.sig=K4F3faYzmVuqC0iXIERCQf55g2Y; xq_is_login=1; xq_is_login.sig=J3LxgPVPUzbBg3Kee_PquUfih7Q; " +
                        "u=5248810825; u.sig=pFGYnw9IW6KGWGczFD_Ll0aIsws; " +
                        "Hm_lvt_fe218c11eab60b6ab1b6f84fb38bcc4a=1557129462,1559268875,1559641881; " +
                        "__utma=1.58317738.1540273939.1560300395.1560481055.129; aliyungf_tc=AQAAAD8rinzRUAIAbrGvtCpuHgKAHuYy; " +
                        "xqat=83fd413aa4f5c9cb30ffbb90dedac5b5e6723854; xqat.sig=dukS6GoktqQPl-zX5HbIcB79GWM; " +
                        "xq_a_token=83fd413aa4f5c9cb30ffbb90dedac5b5e6723854; xq_a_token.sig=2YGYAJTKLZPHcg0X3glOnMC4FJI; " +
                        "xq_r_token=99bac626b8c4769ae339ee5aa3bfd86d95846e30; xq_r_token.sig=UKN1qK-aByst8mw7OlkCzvDYppo; " +
                        "Hm_lvt_1db88642e346389874251b5a1eded6e3=1560418201,1560472353,1560473557,1560731690; snbim_minify=true; " +
                        "Hm_lpvt_1db88642e346389874251b5a1eded6e3=1560755346")
        //表示触发条件
        .when()
                .get("https://xueqiu.com/stock/search.json")
        //对结果断言
        .then()
                .log().all()
                //状态码断言
                .statusCode(200)
                //字段断言
                .body("stocks.name",hasItems("搜狗"))
                .body("stocks.code",hasItems("SOGO"))
                .body("stocks.find{it.code=='SOGO'}.name",equalTo("搜狗"));
    }

    @Test
    public void testLogin(){
        //.proxy("127.0.0.1",8887)
        given()
                .header("Host","api.xueqiu.com")
                .queryParam("code","sogo")
                .queryParam("size","3")
                .queryParam("_s","78ddb6")
                .cookie("u","5248810825")
                .cookie("xq_a_token","a909f58da80d93ced544d76ffdf9cdc79927f870")
                .cookie("uid",code)
        .when()
                .get("https://101.201.175.228/stock/search.json")
        .then()
                .log().all()
                .statusCode(200)
                .body("stocks.find{it.name=='搜狗'}.code",equalTo("SOGO"));
    }

    @Test
    public void testLogin2(){
        useRelaxedHTTPSValidation();
        //.proxy("127.0.0.1",8887)
        //正确的密码->.formParam("password","1897a69ef451f0991bb85c6e7c35aa31")
        Response response = given()
                .header("User-agent","Xueqiu iPhone 11.19.1")
                .header("Host","api.xueqiu.com")
                .queryParam("_","1561100116992")
                .queryParam("x","0.81")
                .queryParam("_s","a7f7bb")
                .queryParam("_t","91E20F96-CBC1-465E-983A-BB621539A3EF.2335155538" +
                        ".1561099721263.1561099721357")
                .cookie("u","2335155538")
                .cookie("xq_a_token","161bb730e56d94dfe7803699bc63dabe23eae8e6")
                .formParam("areacode","86")
                .formParam("grant_type","password")
                .formParam("password","e10adc3949ba59abbe56e057f20f883e")
                .formParam("telephone","13917471023")
                .formParam("client_id","WiCimxpj5H")
                .formParam("client_secret","TM69Da3uPkFzIdxpTEm6hp")
                .formParam("captcha","")
                .formParam("sid","91E20F96-CBC1-465E-983A-BB621539A3EF")
                .formParam("device_uuid","91E20F96-CBC1-465E-983A-BB621539A3EF")
                .when()
                .post("https://101.201.175.228/provider/oauth/token")
                .then()
//                .log().all()
                .statusCode(400)
                .body("error_code",equalTo("20082"))
                .extract().response();

        System.out.println(response.body());
    }

    @Test
    public void testPostJson(){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("a", 1);
        map.put("b", "shrbank");
        map.put("array", new String[] {"111", "2222"});
        given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(map)
        .when().post("http://www.baidu.com")
        .then()
                .log().all()
//                .time(lessThan(1000L))
                .spec(responseSpecification);
                //.statusCode(302);

    }

}
