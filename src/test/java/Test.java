import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Test {
    static class AuthTest {
        @BeforeEach
        void setup() {
            open("http://localhost:9999");
        }

        @org.junit.jupiter.api.Test
        void shouldSuccessfulLoginIfRegisteredActiveUser() {
            var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
            $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
            $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
            $(".button").click();
            $(".heading").shouldBe(Condition.visible);
        }

        @org.junit.jupiter.api.Test
        void shouldGetErrorIfNotRegisteredUser() {
            var notRegisteredUser = DataGenerator.Registration.getUser("active");
            $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
            $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
            $(".button").click();
            $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
        }

        @org.junit.jupiter.api.Test
        void shouldGetErrorIfBlockedUser() {
            var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
            $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
            $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
            $(".button").click();
            $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
        }

        @org.junit.jupiter.api.Test
        void shouldGetErrorIfWrongLogin() {
            var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
            var wrongLogin = DataGenerator.getRandomLogin();
            $("[data-test-id='login'] input").setValue(wrongLogin);
            $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
            $(".button").click();
            $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
        }

        @org.junit.jupiter.api.Test
        void shouldGetErrorIfWrongPassword() {
            var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
            var wrongPassword = DataGenerator.getRandomPassword();
            $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
            $("[data-test-id='password'] input").setValue(wrongPassword);
            $(".button").click();
            $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
        }
    }
}
