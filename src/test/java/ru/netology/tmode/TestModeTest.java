package ru.netology.tmode;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationData;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Condition.text;

public class TestModeTest {

    @BeforeEach
    void setUp() {
        open("http://0.0.0.0:9999");
    }

    @Test
    void correctActiveUser() {
        RegistrationData registrationData = DataGenerator.activeUser();
        $("[data-test-id = 'login'] input").setValue(registrationData.getLogin());
        $("[data-test-id = 'password'] input").setValue(registrationData.getPassword());
        $("button").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void correctBlockedUser() {
        RegistrationData registrationData = DataGenerator.blockedUser();
        $("[data-test-id = 'login'] input").setValue(registrationData.getLogin());
        $("[data-test-id = 'password'] input").setValue(registrationData.getPassword());
        $("button").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Пользователь заблокирован"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldGetErrorIfInvalidPasswordEntered() {

        val registrationData = DataGenerator.getInvalidPasswordUser();

        $("[data-test-id = 'login'] input").setValue(registrationData.getLogin());
        $("[data-test-id = 'password'] input").setValue(registrationData.getPassword());
        $("button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(text("Неверно указан логин или пароль"));

    }

    @Test
    void shouldGetErrorIfInvalidLoginEntered() {

        val registrationData = DataGenerator.getInvalidLoginUser();

        $("[data-test-id = 'login'] input").setValue(registrationData.getLogin());
        $("[data-test-id = 'password'] input").setValue(registrationData.getPassword());
        $("button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(text("Неверно указан логин или пароль"));

    }
}
