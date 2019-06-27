import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Testerhome {
    @Test
    public void testLogin(){
        useRelaxedHTTPSValidation();
        //.proxy("127.0.0.1",8887)
        given()
                .header("User-agent","ozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                " Chrome/69.0.3497.100 Safari/537.36")
                .cookie("_ga","GA1.2.246931038.1540521260")
                .cookie("_gid","GA1.2.1435687323.1560758767")
                .cookie("user_id","MjI5OTE%3D--024dea55385c22a3f5e1e444a02bd2eeb597521c")
                .cookie("hasSkipWelcomePage",1)
                .cookie("_homeland_session","BxU9lUphn8lFinRDidIhcreAew1bkK43I0nUyVYyj1whds4aQgYATKz2vm2su102k2B" +
                        "WNs01diVDKCYkn4J5Uou707e4aGPCXzkAO2DpRLxfnUKag9DxwJRAuw2NXlgS7tPcXNb3kS35CI05kMSd7iq%2BIFIK5c" +
                        "8yXAv0Ic9Tmnq1wbjxtxfUKfhxG4NnWV%2FgA1RsyKOC4BLnStPeWAd7IuTFhLDpcF1eLU88t%2BZz%2Fy8URVwl0am3k" +
                        "Pv5haIE0eqc%2Bo4lU08%2BfZUQwFQMdOaF7zDdLlsrxiXWBg%3D%3D--DBZs%2BZsyfgWXgpJr--4rEtMYQzObmzw9sjtB0mZQ%3D%3D"
                        )
        	    .formParam("utf8","")
                .formParam("user[login]","toygo")
                .formParam("user[password]","111111")
                .formParam("user[remember_me]","0")
                .formParam("commit","登录")
        .when()
                .post("https://testerhome.com/account/sign_in")
        .then()
                .statusCode(200);
    }
}
