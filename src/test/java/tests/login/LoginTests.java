package tests.login;

import annotations.Regression;
import annotations.Smoke;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import listeners.TestListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import steps.LoginSteps;
import tests.BaseTest;

import static pages.LoginPage.MAX_LOGIN_INPUT_LENGTH;
import static pages.LoginPage.MAX_PASSWORD_INPUT_LENGTH;

@Feature("Авторизация")
@DisplayName("Тесты авторизации в приложении Alfa-Test")
@ExtendWith(TestListener.class)
public class LoginTests extends BaseTest {
    private static final String LOGIN_INPUT_VALID_CHARACTERS_REGEX = "[^A-Za-z\\[\\] .,/'_-]";
    private static final String VALID_LOGIN = "Login";
    private static final String VALID_PASSWORD = "Password";
    private LoginSteps loginSteps;

    @BeforeEach
    void setUp() {
        loginSteps = new LoginSteps(driver);
    }

    @Test
    @Smoke
    @DisplayName("Успешная авторизация с валидными данными")
    @Description("Тест проверяет успешную авторизацию с корректными логином и паролем")
    @Story("Авторизация пользователя")
    void testSuccessLogin() {
        loginSteps.enterLoginCredentials(VALID_LOGIN, VALID_PASSWORD);
        loginSteps.clickLoginButton();
        loginSteps.isUserPageOpened();
    }

    @Test
    @Smoke
    @DisplayName("Проверка главного экрана логина")
    @Description("Тест проверяет основные элементы главного экрана")
    @Story("Авторизация пользователя")
    void testLoginPage() {
        loginSteps.isLoginTitleValid();
        loginSteps.verifyLoginButtonEnabled();
        loginSteps.verifyTextOfLoginButton();
        loginSteps.enterLogin("Login1");
        loginSteps.verifyLoginButtonEnabled();
        loginSteps.enterPassword("Pass1");
        loginSteps.verifyLoginButtonEnabled();
    }

    @ParameterizedTest(name = "{index} - {0} не разрешённый символ")
    @ValueSource(strings = {"Login-1", "Login-1%{}"})
    @Regression
    @DisplayName("Проверка разрешённых символов для поля 'Логин'")
    @Description("Тест проверяет допустимые символов для поля 'Логин'")
    @Story("Авторизация пользователя")
    void testLoginInputAllowedCharacters(String value) {
        loginSteps.enterLogin(value);
        loginSteps.clickLoginButton();
        loginSteps.verifyValueOfLoginInput(value.replaceAll(LOGIN_INPUT_VALID_CHARACTERS_REGEX, ""));
        loginSteps.verifyErrorMessage("InvalidValue");
    }

    @Test
    @Smoke
    @DisplayName("Поведение поля 'Пароль' - Маскировка ввода")
    @Description("Тест проверяет маскировку поля 'Пароль' при нажатии на значок видимости пароля")
    @Story("Авторизация пользователя")
    void testPasswordMask() {
        loginSteps.enterPassword("test");
        loginSteps.verifyPasswordMasked(true);
        loginSteps.clickPasswordMasked();
        loginSteps.verifyPasswordMasked(false);
        loginSteps.clickPasswordMasked();
        loginSteps.verifyPasswordMasked(true);
    }

    @Test
    @Regression
    @DisplayName("Проверка максимально допустимой длинны ввода в поле 'Логин'")
    @Description("Тест проверяет максимально допустимой длинны ввода в поле 'Логин'")
    @Story("Авторизация пользователя")
    void testInputLength() {
        String value = RandomStringUtils.insecure().nextAlphabetic(MAX_LOGIN_INPUT_LENGTH);
        loginSteps.enterLogin(value);
        loginSteps.clickLoginButton();
        loginSteps.verifyLengthOfLoginInput();
        String longValue = RandomStringUtils.insecure().nextAlphabetic(MAX_LOGIN_INPUT_LENGTH + 1);
        loginSteps.enterLogin(longValue);
        loginSteps.clickLoginButton();
        loginSteps.verifyLengthOfLoginInput();
    }

    @Test
    @Regression
    @DisplayName("Проверка максимально допустимой длинны ввода в поле 'Пароль'")
    @Description("Тест проверяет максимально допустимой длинны ввода в поле 'Пароль'")
    @Story("Авторизация пользователя")
    void testPasswordLength() {
        String value = RandomStringUtils.insecure().nextAlphabetic(MAX_PASSWORD_INPUT_LENGTH);
        loginSteps.enterPassword(value);
        loginSteps.clickLoginButton();
        loginSteps.verifyLengthOfPasswordInput();
        String longValue = RandomStringUtils.insecure().nextAlphabetic(MAX_PASSWORD_INPUT_LENGTH + 1);
        loginSteps.enterPassword(longValue);
        loginSteps.clickLoginButton();
        loginSteps.verifyLengthOfPasswordInput();
    }
}
