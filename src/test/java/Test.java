import com.codeborne.selenide.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class Test {
    static class AuthTest {
        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        @BeforeAll
        static void setUpAll() {
            given()
                    .spec(requestSpec)
                    .body(new RegistrationDto("vasya", "password", "active"))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }
       @org.junit.jupiter.api.Test
        public void shouldSetForm() {
            Configuration.holdBrowserOpen = true;
            open("http://localhost:9999/");
            $("[data-test-id='login'] input").setValue("vasya");
            $("[data-test-id='password'] input").setValue("password");
            $(".button").click();
        }

    }
}
