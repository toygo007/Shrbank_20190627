import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ShrbankDemoTest {
    @Test
    public void testEasyJson(){
        when()
                .get("http://127.0.0.1:8000/shrbank.json")
        .then()
//                .body("lotto.lottoId",equalTo(6))
                .body("lotto.lottoId",equalTo(5))
//                .body("lotto.winners.winnerId",hasItems(54,23,11))
                .body("lotto.winners.winnerId",hasItems(54,23));
//                .body("**.lottoId",equalTo(5));
    }

    @Test
    public void testStoreJson() {
        when()
                .get("http://127.0.0.1:8000/store.json")
        .then()
                .body("store.book.category",hasItems("reference"))
                .body("store.book[0].author",equalTo("Nigel Rees"))
                .body("store.book[-1].author",equalTo("J. R. R. Tolkien"))
                //findAll 返回的是一个集合
                .body("store.book.findAll { it.price < 10 }.title",hasItems("Moby Dick"))
                .body("store.book.findAll { book ->book.price ==8.95f }.title",hasItems("Sayings of the Century"))
                //注：必须写成8.95f,不能写成8.95
                .body("store.book.find { book ->book.price ==8.95f }.price",equalTo(8.95f));
    }

    @Test
    public void testXML() {
        when()
                .get("http://127.0.0.1:8000/shopping.xml")
        .then()
                .body("shopping.category.item[0].name",equalTo("Chocolate"))
                .body("shopping.category.item.size()",equalTo(5))
                .body("shopping.category.findAll { it.@type == 'groceries' }.size()",equalTo(1))
                .body("shopping.category.item.findAll { it.price == 20 }.name",equalTo("Coffee"))
                .body("**.findAll { it.price == 20 }.name",equalTo("Coffee"))
                .body("shopping.category.item.findAll { it.@when == 'Aug 10'}.name",equalTo("Kathryn's Birthday"))
                .body("**.findAll { it.@type == 'present' }.item.name",equalTo("Kathryn's Birthday"));

    }

    @Test
    public void test1400return() {
        when()
                .get("http://127.0.0.1:8000/1400return.xml")
        .then()
                .body("**.findAll { it.@name == 'RET_MSG' }.field",equalTo("Success"))
                .body("**.findAll { it.@name == 'RET_CODE' }.field",equalTo("000000"));

    }
}
