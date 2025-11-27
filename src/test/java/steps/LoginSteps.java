package steps;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import pages.UserPage;

import static pages.LoginPage.*;

public class LoginSteps {
    private final LoginPage loginPage;
    private final UserPage userPage;

    public LoginSteps(AppiumDriver driver) {
        loginPage = new LoginPage(driver);
        userPage = new UserPage(driver);
    }

    @Step("Проверка заголовка страницы логина")
    public void isLoginTitleValid() {
        Assertions.assertEquals(TITLE_TEXT, loginPage.getLoginTitleText());
    }

    @Step("Ввод логина : логин = {login}")
    public void enterLogin(String login) {
        loginPage.enterLogin(login);
    }

    @Step("Ввод пароля : логин = {password}")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @Step("Ввод логина и пароля: логин = {login}, пароль = {password}")
    public void enterLoginCredentials(String login, String password) {
        loginPage.login(login, password);
    }

    @Step("Нажатие на кнопку входа")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
    }

    @Step("Проверка, что страница 'Вход в Alfa-Test выполнен' открыта")
    public void isUserPageOpened() {
        userPage.isUserPageOpened();
    }

    @Step("Проверка, что кнопка входа активна")
    public void verifyLoginButtonEnabled() {
        Assertions.assertTrue(loginPage.isLoginButtonEnabled(),
                "Кнопка входа должна быть активна всегда");
    }

    @Step("Проверка содержимого поля 'Логин'")
    public void verifyValueOfLoginInput(String value) {
        Assertions.assertEquals(value, loginPage.getLoginText(),
                "Поле 'Логин' содержит ожидаемое значение");
    }

    @Step("Проверка количества символов поля 'Логин'")
    public void verifyLengthOfLoginInput() {
        Assertions.assertEquals(MAX_LOGIN_INPUT_LENGTH, loginPage.getLoginText().length(),
                "Поле 'Логин' содержит ожидаемое количество символов");
    }

    @Step("Проверка количества символов поля 'Пароль'")
    public void verifyLengthOfPasswordInput() {
        Assertions.assertEquals(MAX_PASSWORD_INPUT_LENGTH, loginPage.getPasswordText().length(),
                "Поле 'Пароль' содержит ожидаемое количество символов");
    }

    @Step("Проверка текста кнопки 'Войти'")
    public void verifyTextOfLoginButton() {
        Assertions.assertEquals(LOGIN_BTN_TEXT, loginPage.getLoginBtnTxt(),
                "Текст кнопки 'Войти' содержит ожидаемое значение");
    }

    @Step("Проверка сообщения об ошибке")
    public void verifyErrorMessage(String expectedText) {
        Assertions.assertEquals(expectedText, loginPage.getLoginErrorMessage(),
                "Ожидаемое сообщение об ошибке");
    }

    @Step("Проверка видимости пароля: не виден - {isMasked}")
    public void verifyPasswordMasked(boolean isMasked) {
        Assertions.assertEquals(isMasked, loginPage.isPasswordMasked(),
                "Пароль замаскирован");
    }

    @Step("Нажатие на значок видимости пароля")
    public void clickPasswordMasked() {
        loginPage.togglePasswordVisibility();
    }
}
