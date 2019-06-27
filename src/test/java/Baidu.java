import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Baidu {
    @Test
    public void testGetHtml(){
        given().log().all().get("http://www.baidu.com")
                .then().log().all().statusCode(200);
    }

    @Test
    public void testMp3(){
        /*given().log().all().get("http://www.baidu.com/s?wd=mp3")
                .then().log().all().statusCode(200);*/
        given()
                .queryParam("wd","mp3")
        .when()
                .get("http://www.baidu.com/s")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void getHtml(){
        given()
                .log().all()
                .queryParam("wd","mp3")
        .when()
                .get("http://www.baidu.com/s")
        .then()
                .log().all()
                .statusCode(200)
                .body("html.head.title",equalTo("mp3_百度搜索"));
    }
}
